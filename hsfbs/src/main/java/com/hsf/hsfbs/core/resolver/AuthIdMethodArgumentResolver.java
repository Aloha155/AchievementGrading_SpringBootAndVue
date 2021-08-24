package com.hsf.hsfbs.core.resolver;


import com.hsf.hsfbs.core.annotations.AuthUserId;
import com.hsf.hsfbs.core.exception.SignInException;
import com.hsf.hsfbs.core.util.RegexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * @author yj
 * @date 2021-04-26 11:59
 */
public class AuthIdMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private Logger logger = LoggerFactory.getLogger(AuthIdMethodArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(Integer.class) && methodParameter.hasParameterAnnotation(AuthUserId.class);
    }

    @Override
    public Integer resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        Object userId = nativeWebRequest.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
        if (RegexUtil.checkObjectIsNull(userId)) {
            throw new SignInException("登录信息不能为空");
        }
        try {
            return (Integer) userId;
        } catch (Exception e) {
            throw new SignInException("登录信息错误");
        }
    }
}