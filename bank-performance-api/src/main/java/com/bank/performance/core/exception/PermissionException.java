package com.bank.performance.core.exception;


public class PermissionException extends BaseException {

    public PermissionException(int status, String message, Object data) {
        super(status,message, data);
    }

    public PermissionException(int status, String message) {
        super(status, message);
    }

    public PermissionException(String message) {
        super(message);
        super.setStatus(403);
    }
}
