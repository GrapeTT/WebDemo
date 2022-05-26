package com.demo.web.controller;

import com.demo.api.constant.ErrorCode;
import com.demo.api.model.Message;
import com.demo.common.exception.AppException;
import com.demo.common.tools.ValidateUtils;
import com.demo.web.base.BaseController;
import com.demo.dao.domain.User;
import com.demo.service.UserService;
import com.demo.common.tools.MD5Utils;
import com.demo.common.tools.RSAUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    /**
     * 业务key
     */
    private static final String BUS_KEY = "retrievePassword";
    
    @Resource
    private UserService userService;

    @Autowired
    private ValidateUtils validateUtils;

    /**
     * @Description：获取验证码
     * @Author：涛哥
     * @Time：2019/3/28 19:40
     */
    @RequestMapping(value = "/getValidateCode", method = RequestMethod.POST)
    public @ResponseBody Message<String> getValidateCode( @Param("email") String email) throws Exception {
        if(StringUtils.isEmpty(email)) {
            return Message.failure(ErrorCode.ILLEGAL_PARAM);
        }
        //如果验证码已获取且未过期，则不让用户再次获取
        if(validateUtils.isCodeExsit(BUS_KEY, email)) {
            return Message.failure("验证码未过期，请勿重复获取");
        }
        //验证邮箱是否存在
        User user = userService.getUserByUsername(email);
        if(user == null) {
            return Message.failure("邮箱错误");
        }
        //生成验证码并发送邮件
        if (!validateUtils.genCodeAndSendEmail(email, BUS_KEY, email)) {
            return Message.failure("验证码发送失败，请稍后再试");
        }
       return Message.success("验证码已成功发至邮箱", user.getUid());
    }

    /**
     * @Description：重置密码
     * @Author：涛哥
     * @Time：2019/3/28 19:57
     */
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public @ResponseBody Message<Void> resetPassword(@Param("email") String email, @Param("userValidateCode") String userValidateCode, @Param("uid") String uid, @Param("newPassword") String newPassword) throws Exception {
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(userValidateCode) || StringUtils.isEmpty(uid) || StringUtils.isEmpty(newPassword)) {
            return Message.failure(ErrorCode.ILLEGAL_PARAM);
        }
        //验证验证码
        try {
            if (!validateUtils.validateCode(userValidateCode, BUS_KEY, email)) {
                return Message.failure("验证码错误，请重新输入");
            }
        } catch (AppException e) {
            return Message.failure(e.getMessage());
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
        userService.updateById(user);
        return Message.success("重置密码成功，请登录");
    }
}
