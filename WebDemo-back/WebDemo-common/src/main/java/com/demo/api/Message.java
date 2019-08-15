package com.demo.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Name：Message
 * @Package：com.cjpb.api
 * @Descripition：
 * @Author：涛
 * @Date：2018/8/8 15:11
 * @Version：1.0
 */
public class Message {
    // 编码
    @JsonProperty("code")
    private String code="200";
    
    // 数据
    @JsonProperty("data")
    private Object data;
    
    //结果
    @JsonProperty("message")
    private String message = "ok";
    
    public Message() {
        // 默认构造方法
    }
    
    public Message(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @SuppressWarnings("unchecked")
    public <E> E getData() {
        return (E) data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
    
    public static Message create(String code,String result) {
        return new Message(code, result);
    }
    
    public static Message success() {
        return success("ok");
    }
    
    public static Message success(String msg) {
        return create("200", msg);
    }
    
    public static Message failure() {
        return failure("failure!");
    }
    
    public static Message failure(String msg) {
        return create("000", msg);
    }
    
    public static Message failure(Exception ex) {
        return failure("exception:" + ex.getMessage(), ex);
    }
    
    public static Message failure(String message, Exception ex) {
        if(ex == null) {
            return failure();
        }
        Message msg = failure(message);
        try {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            msg.setData(sw.toString());
        } catch (Exception e) {
        }
        return msg;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
