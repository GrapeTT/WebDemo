package com.demo.controller;

import com.demo.api.Message;
import com.demo.base.BaseController;
import com.demo.domain.User;
import com.demo.service.UserService;
import com.demo.email.EmailClient;
import com.demo.tools.MD5Utils;
import com.demo.tools.RSAUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description：找回密码服务接口
 * @Name：RetrievePasswordController
 * @Package：com.demo.controller
 * @Author：涛哥
 * @Time：2019/3/28 19:35
 * @Version：1.0
 */
@Controller
@RequestMapping("/password")
public class RetrievePasswordController extends BaseController {
    @Resource
    private UserService userService;
    
    @Resource
    private EmailClient emailClient;
    
    //用于保存生成的验证码
    private static Map<String, String> VALIDATECODES = new HashMap<>();
    
    /**
     * @Description：跳转找回密码页面
     * @Author：涛哥
     * @Time：2019/3/31 14:45
     */
    @RequestMapping(value = "/retrieve", method = RequestMethod.GET)
    public String retrievePassword(Model view) throws Exception {
        return "password/retrievePassword";
    }
    
    
    /**
     * @Description：获取验证码
     * @Author：涛哥
     * @Time：2019/3/28 19:40
     */
    @RequestMapping(value = "/getValidateCode", method = RequestMethod.POST)
    public @ResponseBody Message getValidateCode( @Param("email") String email, Model view) throws Exception {
        if(StringUtils.isEmpty(email)) {
            return Message.failure("系统异常，请稍后再试");
        }
        //如果验证码已获取且未过期，则不让用户再次获取
        if(VALIDATECODES.get(email) != null) {
            return Message.failure("验证码未过期，请勿重复获取");
        }
        //验证邮箱是否存在
        User user = userService.getUserByUsername(email);
        if(user == null) {
            return Message.failure("邮箱错误");
        }
        //发送验证码邮件并保存验证码至服务器
        String validateCode = emailClient.sendEmail(email);
        if(validateCode == null) {
            return Message.failure("验证码发送失败，请稍后再试");
        }
        VALIDATECODES.put(email, validateCode);
        //设置验证码5分钟过期
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                VALIDATECODES.remove(email);
                timer.cancel();
            }
        }, 300000);
        Message message = Message.success("验证码已成功发至邮箱");
        message.setData(user.getUid());
        return message;
    }
    
    /**
     * @Description：重置密码
     * @Author：涛哥
     * @Time：2019/3/28 19:57
     */
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public @ResponseBody Message resetPassword(@Param("email") String email, @Param("userValidateCode") String userValidateCode, @Param("uid") String uid, @Param("newPassword") String newPassword, Model view) throws Exception {
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(userValidateCode) || StringUtils.isEmpty(uid) || StringUtils.isEmpty(newPassword)) {
            return Message.failure("系统异常，请稍后再试");
        }
        if(VALIDATECODES.get(email) == null) {
            return Message.failure("验证码已过期，请重新获取");
        }
        String validateCode = VALIDATECODES.get(email);
        if(validateCode.equals(userValidateCode)) {
            VALIDATECODES.remove(email);
        } else {
            return Message.failure("验证码错误，请重新输入");
        }
        newPassword = RSAUtils.decrypt(newPassword);
        if(newPassword == null) {
            return Message.failure("重置密码失败，请稍后再试");
        } else {
            newPassword = MD5Utils.encrypt(newPassword);
        }
        //更新用户登录信息
        User user = userService.getUserByUid(uid);
        user.setPassword(newPassword);
        user.setUpdateTime(new Date());
        userService.updateByPrimaryKey(user);
        return Message.success("重置密码成功，请登录");
    }
}
