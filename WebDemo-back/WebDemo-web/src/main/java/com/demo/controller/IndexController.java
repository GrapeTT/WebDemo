package com.demo.controller;

import com.demo.base.BaseController;
import com.demo.tools.SessionUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * @Name：IndexController
 * @Package：com.cjpb.controller
 * @Descripition：
 * @Author：涛
 * @Date：2018/3/29 16:32
 * @Version：1.0
 */
@Controller
public class IndexController extends BaseController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndex(HttpSession session, Model view) {
        if("register".equals(SessionUtils.getUid(session))) {
            session.invalidate();
        }
        return "login";
    }
}