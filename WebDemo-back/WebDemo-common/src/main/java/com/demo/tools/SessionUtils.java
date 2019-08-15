package com.demo.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpSession;

/**
 * @Description：session工具类
 * @Name：SessionUtils
 * @Package：com.swust.lpms.tools
 * @Author：涛哥
 * @Time：2019/4/21 23:35
 * @Version：1.0
 */
public class SessionUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionUtils.class);
    
    /**
     * @Description：获取uid
     * @Author：涛哥
     * @Time：2019/4/21 23:37
     */
    public static String getUid(HttpSession session){
        Object uid = session.getAttribute("uid");
        if(uid == null) {
            return null;
        }
        return String.valueOf(uid);
    }
    
    /**
     * @Description：获取userpin
     * @Author：涛哥
     * @Time：2019/4/21 23:37
     */
    public static String getUserpin(HttpSession session){
        Object userpin = session.getAttribute("userpin");
        if(userpin == null) {
            return null;
        }
        return String.valueOf(userpin);
    }
    
    /**
     * @Description：获取role
     * @Author：涛哥
     * @Time：2019/4/21 23:38
     */
    public static Integer getRole(HttpSession session){
        Object role = session.getAttribute("role");
        if(role == null) {
            return null;
        }
        return Integer.valueOf(String.valueOf(role));
    }
}
