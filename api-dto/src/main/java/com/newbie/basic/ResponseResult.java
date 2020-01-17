package com.newbie.basic;


import com.newbie.basic.enums.ResponseTypes;

/**
 * create by 王飞 on 2020-01-14
 */
public class ResponseResult<T> {
    private String code;
    private boolean success;
    private String message;
    private T data;

    public ResponseResult() {
    }

    public ResponseResult(T data) {
        if (data instanceof ResponseTypes) {
            boolean success = data == ResponseTypes.SUCCESS;
            this.success = success;
            this.code = success ? ResponseTypes.SUCCESS.getCode() : ((ResponseTypes)data).getCode();
            this.message = success ? ResponseTypes.SUCCESS.getDesc() : ((ResponseTypes)data).getDesc();
        } else {
            this.success = true;
            this.code = ResponseTypes.SUCCESS.getCode();
            this.message = ResponseTypes.SUCCESS.getDesc();
            this.data = data;
        }

    }

    public ResponseResult(String message, T data) {
        this.success = true;
        this.code = ResponseTypes.SUCCESS.getCode();
        this.message = message;
        this.data = data;
    }

    public ResponseResult(ResponseTypes type, String message) {
        this.success = type.equals(ResponseTypes.SUCCESS);
        this.code = type.getCode();
        this.message = message;
        this.data = null;
    }

    public ResponseResult(ResponseTypes type, String message, T data) {
        this.success = type.equals(ResponseTypes.SUCCESS);
        this.code = type.getCode();
        this.message = message;
        this.data = data;
    }

    public ResponseResult(Boolean success, String code, String message, T data) {
        this.success = success;
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

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ResponseResult builder() {
        return new ResponseResult();
    }

    public ResponseResult success(T data) {
        this.success = true;
        this.code = ResponseTypes.SUCCESS.getCode();
        this.data = data;
        return this;
    }

    public ResponseResult message(String message) {
        this.message = message;
        return this;
    }
}
