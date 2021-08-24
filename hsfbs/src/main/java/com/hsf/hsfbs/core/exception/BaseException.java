package com.hsf.hsfbs.core.exception;

public class BaseException extends RuntimeException {
    private int status = 500;

    private Object data;

    public final int getStatus() {
        return this.status;
    }

    public final void setStatus(int var1) {
        this.status = var1;
    }

    public final Object getData() {
        return this.data;
    }

    public final void setData(Object var1) {
        this.data = var1;
    }

    public BaseException(int status, String message, Object data) {
        super(message);
        this.status = status;
        this.data = data;
    }

    public BaseException(int status, String message) {
        super(message);
        this.status = status;
    }

    public BaseException(String message) {
        super(message);
    }
}
