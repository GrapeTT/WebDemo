package com.demo.api.constant;

import java.io.Serializable;

/**
 * @Name：Message
 * @Package：com.demo.api.constant
 * @Descripition：错误码定义
 * @Author：涛
 * @Date：2022/5/26 15:36
 * @Version：1.0
 */
public enum ErrorCode implements Serializable {
    SUCCESS(200, "操作成功"),
    SERVER_ERROR(500, "系统异常，请稍后再试"),
    FAILURE(5001, "操作失败"),
    ILLEGAL_PARAM(5002, "参数不合法");
    
    /**
     * code
     */
    private int code;
    
    /**
     * msg
     */
    private String msg;
    
    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getMsg() {
        return msg;
    }
}
