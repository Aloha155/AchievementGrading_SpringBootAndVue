package com.hsf.hsfbs.core.exception;


public class NotFoundException extends BaseException {
    public NotFoundException(int status, String message, Object data) {
        super(status, message, data);
    }

    public NotFoundException(int status, String message) {
        super(status, message);
    }

    public NotFoundException(String message) {
        super(message);
        super.setStatus(404);
    }
}
