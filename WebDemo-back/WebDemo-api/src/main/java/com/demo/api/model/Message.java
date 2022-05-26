package com.demo.api.model;

/**
 * @Name：Message
 * @Package：com.cjpb.api
 * @Descripition：
 * @Author：涛
 * @Date：2018/8/8 15:11
 * @Version：1.0
 */
import java.io.Serializable;

public class Message<T> implements Serializable {
    public static final String SUCCESS_CODE = "200";
    public static final String INNERT_ERROR_CODE = "500";
    public static final String ERROR_CODE = "5001";
    
    private String code = SUCCESS_CODE;
    private T data;
    private String message = "ok";
    
    public Message() {
    }
    
    public Message(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public Message(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public T getData() {
        return this.data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public static <T> Message<T> create(String code, String message) {
        return new Message(code, message);
    }
    
    public static <T> Message<T> create(String code, String message, T data) {
        return new Message(code, message, data);
    }
    
    public static <T> Message<T> success() {
        return success("ok");
    }
    
    public static <T> Message<T> success(String message) {
        return create(SUCCESS_CODE, message);
    }
    
    public static <T> Message<T> success(String message, T data) {
        return create(SUCCESS_CODE, message, data);
    }
    
    public static <T> Message<T> writeData(T data) {
        Message<T> message = success();
        message.setData(data);
        return message;
    }
    
    public static <T> Message<T> failure() {
        return failure("failure!");
    }
    
    public static <T> Message<T> failure(String message) {
        return create(ERROR_CODE, message);
    }
    
    public static <T> Message<T> failure(String code, String message) {
        return create(code, message);
    }
    
    public static <T> Message<T> failure(Exception ex) {
        return failure("exception:" + ex.getMessage());
    }
    
    public static <T> Message<T> failure(String code, Exception ex) {
        Message<T> message = failure(ex);
        message.setCode(code);
        return message;
    }
    
    public boolean isSuccess() {
        return SUCCESS_CODE == this.code;
    }
}
