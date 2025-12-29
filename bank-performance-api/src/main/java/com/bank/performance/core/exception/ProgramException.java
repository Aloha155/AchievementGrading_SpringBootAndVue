package com.bank.performance.core.exception;

public class ProgramException extends BaseException {
    public ProgramException(int status, String message, Object data) {
        super(status, message, data);
    }

    public ProgramException(int status, String message) {
        super(status, message);
    }

    public ProgramException(String message) {
        super(message);
        super.setStatus(500);
    }

}
