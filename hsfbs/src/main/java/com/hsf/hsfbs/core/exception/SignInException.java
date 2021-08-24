package com.hsf.hsfbs.core.exception;


public class SignInException extends BaseException {
    public SignInException(int status, String message, Object data) {
        super(status, message, data);
    }

    public SignInException(int status, String message) {
        super(status, message);
    }

    public SignInException(String message) {
        super(message);
        super.setStatus(401);
    }
}
