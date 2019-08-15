package com.demo.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Name：DateConverter
 * @Package：com.swust.lpms.util
 * @Descripition：json日期格式转换
 * @Author：涛
 * @Date：2018/4/1 15:09
 * @Version：1.0
 */
public class DateConverter extends SimpleDateFormat {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateConverter.class);
    
    @Override
    public Date parse(String source) throws ParseException {
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(source);
        try {
            //转成直接返回
            return simpleDateFormat.parse(source);
        } catch (Exception e) {
            LOGGER.error("转换时间失败，date=" + source, e);
        }
        //如果转换失败返回null
        return null;
    }
    
    private SimpleDateFormat getSimpleDateFormat(String source) {
        SimpleDateFormat simpleDateFormat = null;
        if(source.contains("-") && source.contains(":")) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if(source.contains("-") && !source.contains(":")) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        } else if(source.contains("/") && source.contains(":")) {
            simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        } else if(source.contains("/") && !source.contains(":")) {
            simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        }
        return simpleDateFormat;
    }
}
