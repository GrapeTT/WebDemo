package com.demo.controller;

import com.demo.api.Message;
import com.demo.base.BaseController;
import com.demo.domain.User;
import com.demo.email.EmailClient;
import com.demo.exception.CustomException;
import com.demo.redis.RedisClient;
import com.demo.service.UserService;
import com.demo.tools.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Author：涛
 * @Descripition：
 * @Date：2018/3/19 22:41
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;
    
    @Resource
    private EmailClient emailClient;
    
    //用于保存生成的验证码
    private static Map<String, String> VALIDATECODES = new HashMap<>();
    
    /**
     * @Description：登录
     * @Author：涛哥
     * @Time：2019/3/6 16:54
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody Message login(@RequestBody User userCondition, HttpSession session, Model view) throws Exception {
        //校验账号、密码是否正确
        String password = userCondition.getPassword();
        password = RSAUtils.decrypt(password);
        if(password != null) {
            password = MD5Utils.encrypt(password);
        } else {
            return Message.failure("服务器异常，请稍后再试");
        }
        userCondition.setPassword(password);
        User user = userService.selectOneByCondition(userCondition);
        if(user == null) {
            return Message.failure("账号或密码错误");
        }
        //设置session
        session.setAttribute("uid", user.getUid());
        session.setAttribute("userpin", "游客");
        session.setAttribute("DateUtils", new DateUtils());
        //设置session过期时间（半小时）
        session.setMaxInactiveInterval(1800);
        return Message.success("登录成功");
    }
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) throws Exception {
        request.getSession().invalidate();
        return "login";
    }
    
    /**
     * @Description：获取注册用户界面
     * @Author：涛哥
     * @Time：2019/4/13 16:17
     */
    @RequestMapping(value = "/getRegisterPage", method = RequestMethod.GET)
    public String getRegisterPage(HttpSession session, Model view) throws Exception {
        //设置注册session
        session.setAttribute("uid", "register");
        //设置session过期时间（半小时）
        session.setMaxInactiveInterval(1800);
        return "register";
    }
    
    /**
     * @Description：获取验证码
     * @Author：涛哥
     * @Time：2019/4/15 1:01
     */
    @RequestMapping(value = "/getValidateCode", method = RequestMethod.POST)
    public @ResponseBody Message getValidateCode(@Param("email") String email, Model view) throws Exception {
        if(StringUtils.isEmpty(email)) {
            return Message.failure("系统异常，请稍后再试");
        }
        //如果验证码已获取且未过期，则不让用户再次获取
        if(VALIDATECODES.get(email) != null) {
            return Message.failure("验证码未过期，请勿重复获取");
        }
        //验证邮箱是否已注册
        if(userService.isRepeat(email)) {
            return Message.failure("该邮箱已注册，请重新输入");
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
        return Message.success("验证码已成功发至邮箱");
    }
    
    /**
     * @Description：注册用户
     * @Author：涛哥
     * @Time：2019/4/15 2:20
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody Message register(User user, String userValidateCode, HttpSession session) throws Exception {
        if(user == null || StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            return Message.failure("对不起，您没有权限");
        }
        if(StringUtils.isEmpty(userValidateCode)) {
            return Message.failure("对不起，您没有权限");
        }
        //判重
        if(userService.isRepeat(user.getUsername())) {
            return Message.failure("该邮箱已注册，请重新输入");
        }
        if(userService.isRepeat(user.getUsername())) {
            return Message.failure("该账号已存在，请重新输入");
        }
        //注册用户
        String password;
        //校验验证码
        String email = user.getUsername();
        if (VALIDATECODES.get(email) == null) {
            return Message.failure("验证码已过期，请重新获取");
        }
        String validateCode = VALIDATECODES.get(email);
        if (!validateCode.equals(userValidateCode)) {
            return Message.failure("验证码错误，请重新输入");
        }
        //处理密码
        password = user.getPassword();
        password = RSAUtils.decrypt(password);
        if(password != null) {
            password = MD5Utils.encrypt(password);
        } else {
            return Message.failure("服务器异常，请稍后再试");
        }
        user.setPassword(password);
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);
        userService.insertEntry(user);
        //生成uid
        User newUser = userService.getUserByUsername(email);
        String uid = UidUtils.generate(newUser.getId());
        if(uid != null) {
            newUser.setUid(uid);
            userService.updateByPrimaryKey(newUser);
        }
        //清除验证码
        VALIDATECODES.remove(email);
        return Message.success();
    }
}