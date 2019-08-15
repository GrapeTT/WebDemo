package com.demo.exception;

import com.demo.api.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @Name：CustomExceptionResolver
 * @Package：com.swust.lpms.exception
 * @Descripition：全局异常处理器
 * @Author：涛
 * @Date：2018/4/7 14:16
 * @Version：1.0
 */
@ControllerAdvice
public class CustomExceptionResolver {
    /**
     * @Description：500
     * @Author：涛哥
     * @Time：2019/3/6 16:54
     */
    @ExceptionHandler(value = Exception.class)
    public @ResponseBody Message resolve500(Exception e, Model view) {
        CustomException customException;
        if(e instanceof CustomException){
            customException = (CustomException)e;
        } else {
            customException = new CustomException("对不起，服务器发生未知错误");
        }
        return Message.failure(customException.getMessage());
    }
}
