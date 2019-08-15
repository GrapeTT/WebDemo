package com.demo.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

/**
 * @Description：权限验证工具类
 * @Name：PermissionUtils
 * @Package：com.swust.lpms.tools
 * @Author：涛哥
 * @Time：2019/4/28 0:43
 * @Version：1.0
 */
public class PermissionUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionUtils.class);
    
    /**
     * @Description：验证是否具有权限
     * @Author：涛哥
     * @Time：2019/4/28 0:45
     */
    public static Boolean isAuth(HttpSession session, Integer type) {
        if(session == null || type == null) {
            return null;
        }
        if(type == 1) {
            //验证超级管理员权限
            if(SessionUtils.getRole(session) == 1) {
                return true;
            } else {
                return false;
            }
        } else if(type == 2) {
            //验证管理员权限
            if(SessionUtils.getRole(session) < 3) {
                return true;
            } else {
                return false;
            }
        } else if(type == 3) {
            //验证实验室负责人权限
            if(SessionUtils.getRole(session) == 3) {
                return true;
            } else {
                return false;
            }
        } else if(type == 4) {
            //验证实验室教师权限
            if(SessionUtils.getRole(session) > 2 || SessionUtils.getRole(session) < 5) {
                return true;
            } else {
                return false;
            }
        } else {
            //验证实验室学生权限
            if(SessionUtils.getRole(session) == 5) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    /**
     * @Description：验证是否不具有权限
     * @Author：涛哥
     * @Time：2019/4/28 0:53
     */
    public static Boolean isNotAuth(HttpSession session, Integer type) {
        return !isAuth(session, type);
    }
}
