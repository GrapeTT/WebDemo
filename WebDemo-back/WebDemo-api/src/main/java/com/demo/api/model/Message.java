package com.demo.api.model;

/**
 * @Name：Message
 * @Package：com.demo.api.model
 * @Descripition：
 * @Author：涛
 * @Date：2018/8/8 15:11
 * @Version：1.0
 */
import com.demo.api.constant.ErrorCode;

import java.io.Serializable;

public class Message<T> implements Serializable {
    private Integer code = ErrorCode.SUCCESS.getCode();
    private T data;
    private String message = ErrorCode.SUCCESS.getMsg();
    
    public Message() {
    }
    
    public Message(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public Message(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public void setCode(Integer code) {
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
    
    public static <T> Message<T> create(Integer code, String message) {
        return new Message(code, message);
    }
    
    public static <T> Message<T> create(Integer code, String message, T data) {
        return new Message(code, message, data);
    }
    
    public static <T> Message<T> success() {
        return success(ErrorCode.SUCCESS);
    }
    
    public static <T> Message<T> success(ErrorCode errorCode) {
        return create(errorCode.getCode(), errorCode.getMsg());
    }
    
    public static <T> Message<T> success(String message) {
        return create(ErrorCode.SUCCESS.getCode(), message);
    }
    
    public static <T> Message<T> success(String message, T data) {
        return create(ErrorCode.SUCCESS.getCode(), message, data);
    }
    
    public static <T> Message<T> successWithData(T data) {
        Message<T> message = success();
        message.setData(data);
        return message;
    }
    
    public static <T> Message<T> failure() {
        return failure(ErrorCode.FAILURE);
    }
    
    public static <T> Message<T> failure(ErrorCode errorCode) {
        return create(errorCode.getCode(), errorCode.getMsg());
    }
    
    public static <T> Message<T> failure(String message) {
        return create(ErrorCode.FAILURE.getCode(), message);
    }
    
    public static <T> Message<T> failure(Integer code, String message) {
        return create(code, message);
    }
    
    public static <T> Message<T> failure(Exception ex) {
        return failure("exception:" + ex.getMessage());
    }
    
    public static <T> Message<T> failure(Integer code, Exception ex) {
        Message<T> message = failure(ex);
        message.setCode(code);
        return message;
    }
    
    public boolean isSuccess() {
        return ErrorCode.SUCCESS.getCode() == this.code;
    }
}
