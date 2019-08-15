package com.demo.exception;

/**
 * @Name：CustomException
 * @Package：com.swust.lpms.exception
 * @Descripition：系统自定义的异常类，针对预期的异常，需要在程序中抛出此类的异常
 * @Author：涛
 * @Date：2018/4/7 14:06
 * @Version：1.0
 */
public class CustomException  extends Exception{
    //编码
    private String code= "500";
    //异常信息
    private String message;
    
    public CustomException() {}
    
    public CustomException(String message) {
        super(message);
        this.message = message;
    }
    
    public CustomException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
