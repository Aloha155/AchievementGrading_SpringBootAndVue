package com.hsf.hsfbs.core.advice;


import com.hsf.hsfbs.core.annotations.RestFulPack;
import com.hsf.hsfbs.core.exception.NotFoundException;
import com.hsf.hsfbs.core.exception.PermissionException;
import com.hsf.hsfbs.core.exception.ProgramException;
import com.hsf.hsfbs.core.exception.SignInException;
import com.hsf.hsfbs.core.model.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@ControllerAdvice("com.hsf")
public class GlobalHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        Method method = methodParameter.getMethod();
        return method != null && method.isAnnotationPresent(RestFulPack.class);
    }


    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        return (o instanceof Result) ? (Result<?>) o : Result.returnGenEriCity(o);
    }


    @ResponseBody
    @ExceptionHandler({RuntimeException.class})
    public Result<Throwable> exceptionHandler(RuntimeException e) {
        return Result.returnError(500, e.getMessage(), e.getCause());
    }

    @ResponseBody
    @ExceptionHandler({NullPointerException.class})
    public Result<Throwable> nullPointerExceptionHandler(NullPointerException e) {
        return Result.returnError(500, e.getMessage(), e.getCause());
    }

    @ResponseBody
    @ExceptionHandler({SQLException.class})
    public Result<Throwable> sqlExceptionHandler(SQLException e) {
        return Result.returnError(500, e.getMessage(), e.getCause());
    }

    @ResponseBody
    @ExceptionHandler({NotFoundException.class})
    public Result<Throwable> notFoundExceptionHandler(NotFoundException e) {
        return Result.returnError(e.getStatus(), e.getMessage(), e.getCause());
    }

    @ResponseBody
    @ExceptionHandler({PermissionException.class})
    public Result<Throwable> permissionExceptionHandler(PermissionException e) {
        return Result.returnError(e.getStatus(), e.getMessage(), e.getCause());
    }

    @ResponseBody
    @ExceptionHandler({SignInException.class})
    public Result<Throwable> signInExceptionHandler(SignInException e) {
        return Result.returnError(e.getStatus(), e.getMessage(), e.getCause());
    }

    @ResponseBody
    @ExceptionHandler({ProgramException.class})
    public Result<Throwable> programExceptionHandler(ProgramException e) {
        return Result.returnError(e.getStatus(), e.getMessage(), e.getCause());
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public Result<Object> exceptionHandler(Exception e) {
        return Result.returnError(500, e.getMessage());
    }

    /**
     * 校验异常
     */
    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result<Object> exceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder message = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            message.append(fieldError.getDefaultMessage()).append("!");
        }
        return Result.returnError(500, message.toString());
    }

    /**
     * 校验异常
     */
    @ResponseBody
    @ExceptionHandler({BindException.class})
    public Result<Object> validationExceptionHandler(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder message = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            message.append(fieldError.getDefaultMessage()).append("!");
        }
        return Result.returnError(500, message.toString());
    }

    /**
     * 校验异常
     */
    @ResponseBody
    @ExceptionHandler({ConstraintViolationException.class})
    public Result<Object> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        List<String> msgList = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<?> cvl = iterator.next();
            msgList.add(cvl.getMessageTemplate());
        }
        return Result.returnError(500, String.join(",", msgList));
    }

}
