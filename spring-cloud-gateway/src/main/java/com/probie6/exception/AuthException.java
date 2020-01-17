package com.probie6.exception;

/**
 * create by wangfei on 2020-01-16
 */
public class AuthException extends RuntimeException {
    public AuthException(String msg, Throwable e) {
        super(msg, e);
    }

    public AuthException(String msg) {
        super(msg);
    }
}
