package com.hsf.hsfbs.core.model;

import lombok.Data;

@Data
public class Result<T> {
    private int status;

    private String message;

    private T data;

    private Result(T data) {
        this.status = 200;
        this.data = data;
    }

    private Result(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private Result(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }


    public static <T> Result<T> returnDefault() {
        return returnGenEriCity(null);
    }

    public static Result<Boolean> returnDefaultTrue() {
        return returnGenEriCity(true);
    }

    public static Result<Boolean> returnDefaultFalse() {
        return returnGenEriCity(false);
    }

    public static <T> Result<T> returnGenEriCity(T t) {
        return new Result<T>(t);
    }

    public static <T> Result<T> returnError(int status, String message, T data) {
        return new Result<T>(status, message, data);
    }

    public static <T> Result<T> returnError(int status, String message) {
        return new Result<T>(status, message);
    }

}
