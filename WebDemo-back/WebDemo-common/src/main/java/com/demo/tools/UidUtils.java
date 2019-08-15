package com.demo.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

/**
 * @Description：uid生成工具类
 * @Name：UidUtils
 * @Package：com.swust.lpms.tools
 * @Author：涛哥
 * @Time：2019/4/25 18:26
 * @Version：1.0
 */
public class UidUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(UidUtils.class);
    
    //用于填充8位Uid
    private static String[] FILLSTRING = {"000000", "00000", "0000", "000", "00", "0", ""};
    
    /**
     * @Description：生成uid
     * @Author：涛哥
     * @Time：2019/4/25 18:39
     */
    public static String generate(Long id) {
        if (id == null) {
            LOGGER.error("生成uid失败，id为null");
            return null;
        }
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        year = year.substring(year.length() - 2, year.length());
        String idStr = String.valueOf(id);
        if(idStr .length() < 7) {
            return year + FILLSTRING[idStr.length()] + idStr;
        } else {
            return year + idStr;
        }
    }
}
