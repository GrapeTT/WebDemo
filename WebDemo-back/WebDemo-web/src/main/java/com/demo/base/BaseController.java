package com.demo.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Name：BaseController
 * @Package：com.cjpb.base
 * @Descripition：
 * @Author：涛
 * @Date：2018/9/4 14:38
 * @Version：1.0
 */
public class BaseController {
    public static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);
    
    /**
     * @Description：key-value日期格式转换
     * @Author：涛哥
     * @Time：2019/4/16 15:49
     */
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }
}
