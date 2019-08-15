package com.demo.tools;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description：日期格式转换
 * @Name：DateUtils
 * @Package：com.swust.lpms.tools
 * @Author：涛哥
 * @Time：2019/4/15 16:04
 * @Version：1.0
 */
public class DateUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
    
    /**
     * @Description：Date转String（yyyy-MM-dd HH:mm:ss）
     * @Author：涛哥
     * @Time：2019/4/15 16:06
     */
    public static String format(Date date) {
        if(date == null) {
            LOGGER.error("转换时间失败，date=" + date);
            return null;
        }
        return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * @Description：Date转String（yyyy/MM/dd）
     * @Author：涛哥
     * @Time：2019/4/21 15:42
     */
    public static String formatDate(Date date) {
        if(date == null) {
            LOGGER.error("转换时间失败，date=" + date);
            return null;
        }
        return DateFormatUtils.format(date, "yyyy-MM-dd");
    }
    
    /**
     * @Description：String转Date
     * @Author：涛哥
     * @Time：2019/4/15 16:07
     */
    public static Date parse(String date) {
        if(StringUtils.isEmpty(date)) {
            LOGGER.error("转换时间失败，date=" + date);
            return null;
        }
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(date);
        try {
            return simpleDateFormat.parse(date);
        } catch (Exception e) {
            LOGGER.error("转换时间失败，date=" + date, e);
        }
        return null;
    }
    
    /**
     * @Description：匹配字符串模式，返回SimpleDateFormat对象
     * @Author：涛哥
     * @Time：2019/4/22 13:03
     */
    private static SimpleDateFormat getSimpleDateFormat(String source) {
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
    
    /**
     * @Description：date转time
     * @Author：涛哥
     * @Time：2019/5/1 10:58
     */
    public static String dateToTimeString(Date date) {
        if(date == null) {
            LOGGER.error("转换时间失败，date=null");
            return null;
        }
        return DateFormatUtils.format(date, "HH:mm:ss");
    }
    
    /**
     * @Description：计算两个日期的天数差
     * @Author：涛哥
     * @Time：2019/4/21 16:18
     */
    public static Integer getDaysDistance(Date start, Date end) {
        if(start == null || end == null) {
            LOGGER.error("计算失败，start=" + start + "，end=" + end);
            return null;
        }
        String startString = formatDate(start) + " 00:00:00";
        String endString = formatDate(end) + " 24:00:00";
        if(startString.equals(endString)) {
            return 0;
        }
        start = parse(startString);
        end = parse(endString);
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        int dayStart = calStart.get(Calendar.DAY_OF_YEAR);
        int dayEnd = calEnd.get(Calendar.DAY_OF_YEAR);
        int yearStart = calStart.get(Calendar.YEAR);
        int yearEnd = calEnd.get(Calendar.YEAR);
        if(yearStart != yearEnd)   //不同年
        {
            int timeDistance = 0 ;
            for(int i = yearStart; i < yearEnd; i++)
            {
                if((i % 4 == 0 && i % 100 != 0) || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            return timeDistance + (dayEnd - dayStart);
        }
        else    //同年
        {
            return dayEnd - dayStart;
        }
    }
    
    /**
     * @Description：判断两个日期是否相等
     * @Author：涛哥
     * @Time：2019/5/1 11:06
     */
    public static Boolean isEqual(Date date1, Date date2) {
        if(date1 == null || date2 == null) {
            LOGGER.error("判断失败，date1=" + date1 + "，date2=" + date2);
            return null;
        }
        String date1String = formatDate(date1);
        String date2String = formatDate(date2);
        if(date1String.equals(date2String)) {
            return true;
        }
        return false;
    }
    
    /**
     * @Description：判断date1是否在date2之前
     * @Author：涛哥
     * @Time：2019/4/22 14:59
     */
    public static Boolean before(Date date1, Date date2) {
        if(date1 == null || date2 == null) {
            LOGGER.error("判断失败，date1=" + date1 + "，date2=" + date2);
            return null;
        }
        String date1String = formatDate(date1) + " 24:00:00";
        String date2String = formatDate(date2) + " 24:00:00";
        if(date1String.equals(date2String)) {
            return true;
        }
        date1 = parse(date1String);
        date2 = parse(date2String);
        return date1.before(date2);
    }
    
    /**
     * @Description：判断date1是否在date2之后
     * @Author：涛哥
     * @Time：2019/4/22 14:59
     */
    public static Boolean after(Date date1, Date date2) {
        if(date1 == null || date2 == null) {
            LOGGER.error("判断失败，date1=" + date1 + "，date2=" + date2);
            return null;
        }
        String date1String = formatDate(date1) + " 24:00:00";
        String date2String = formatDate(date2) + " 24:00:00";
        if(date1String.equals(date2String)) {
            return true;
        }
        date1 = parse(date1String);
        date2 = parse(date2String);
        return date1.after(date2);
    }
    
    /**
     * @Description：获取包括今日在内的过去一周的日期列表
     * @Author：涛哥
     * @Time：2019/5/2 16:40
     */
    public static List<Date> getLastWeekDateList() {
        List<Date> dateList = new ArrayList<>();
        Date date = new Date();
        dateList.add(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        for(int i = 1; i < 7; i++) {
            calendar.add(Calendar.DATE, -1);
            dateList.add(calendar.getTime());
        }
        return dateList;
    }
    
    /**
     * @Description：获取包括今日在内的过去一周的日期
     * @Author：涛哥
     * @Time：2019/5/3 15:41
     */
    public static Date getLastWeekDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -6);
        return calendar.getTime();
    }
    
    /**
     * @Description：获取包括今日在内的过去一月的日期
     * @Author：涛哥
     * @Time：2019/5/3 15:42
     */
    public static Date getLastMonthDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -29);
        return calendar.getTime();
    }
}
