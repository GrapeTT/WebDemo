package com.demo.web.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.demo.api.constant.ErrorCode;
import com.demo.api.model.Message;
import com.demo.common.exception.AppException;
import com.demo.common.tools.ValidateUtils;
import com.demo.web.base.BaseController;
import com.demo.common.tools.DateUtils;
import com.demo.common.tools.MD5Utils;
import com.demo.common.tools.RSAUtils;
import com.demo.common.tools.UidUtils;
import com.demo.dao.domain.User;
import com.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    /**
     * 业务key
     */
    private static final String BUS_KEY = "register";
    
    @Resource
    private UserService userService;

    @Autowired
    private ValidateUtils validateUtils;

    /**
     * @Description：登录
     * @Author：涛哥
     * @Time：2019/3/6 16:54
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody Message<Void> login(@RequestBody User condition, HttpSession session) throws Exception {
        //校验账号、密码是否正确
        String password = condition.getPassword();
        password = RSAUtils.decrypt(password);
        if(password != null) {
            password = MD5Utils.encrypt(password);
        } else {
            return Message.failure(ErrorCode.ILLEGAL_PARAM);
        }
        condition.setPassword(password);
        User user = userService.getOne(Wrappers.query(condition));
        if(user == null) {
            return Message.failure("账号或密码错误");
        }
        //设置session
        session.setAttribute("uid", user.getUid());
        session.setAttribute("userpin", user.getUsername());
        session.setAttribute("DateUtils", new DateUtils());
        //设置session过期时间（半小时）
        session.setMaxInactiveInterval(1800);
        return Message.success("登录成功");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public @ResponseBody Message<Void> logout(HttpServletRequest request) throws Exception {
        request.getSession().invalidate();
        return Message.success("登出成功");
    }

    /**
     * @Description：获取验证码
     * @Author：涛哥
     * @Time：2019/4/15 1:01
     */
    @RequestMapping(value = "/getValidateCode", method = RequestMethod.POST)
    public @ResponseBody Message<Void> getValidateCode(@Param("email") String email) throws Exception {
        if(StringUtils.isEmpty(email)) {
            return Message.failure(ErrorCode.ILLEGAL_PARAM);
        }
        //如果验证码已获取且未过期，则不让用户再次获取
        if(validateUtils.isCodeExist(BUS_KEY, email)) {
            return Message.failure("验证码未过期，请勿重复获取");
        }
        //验证邮箱是否已注册
        if(userService.isRepeat(email)) {
            return Message.failure("该邮箱已注册，请重新输入");
        }
        //生成验证码并发送邮件
        if (!validateUtils.genCodeAndSendEmail(email, BUS_KEY, email)) {
            return Message.failure("验证码发送失败，请稍后再试");
        }
        return Message.success("验证码已成功发至邮箱");
    }

    /**
     * @Description：注册用户
     * @Author：涛哥
     * @Time：2019/4/15 2:20
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody Message<Void> register(User user, String userValidateCode, HttpSession session) throws Exception {
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
        //注册用户
        String password;
        //验证验证码
        String email = user.getUsername();
        try {
            if (!validateUtils.validateCode(userValidateCode, BUS_KEY, email)) {
                return Message.failure("验证码错误，请重新输入");
            }
        } catch (AppException e) {
            return Message.failure(e.getMessage());
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
        userService.save(user);
        //生成uid
        User newUser = userService.getUserByUsername(email);
        String uid = UidUtils.generate(newUser.getId());
        if(uid != null) {
            newUser.setUid(uid);
            userService.updateById(newUser);
        }
        return Message.success();
    }
}