package com.demo.common.tools;

import cn.hutool.log.Log;
import javax.servlet.http.HttpSession;

/**
 * @Description：session工具类
 * @Name：SessionUtils
 * @Package：com.demo.tools
 * @Author：涛哥
 * @Time：2019/4/21 23:35
 * @Version：1.0
 */
public class SessionUtils {
    private static final Log LOG = Log.get();
    
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
}
