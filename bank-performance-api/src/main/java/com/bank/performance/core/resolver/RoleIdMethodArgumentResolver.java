package com.bank.performance.core.resolver;


import com.bank.performance.core.annotations.AuthRoleId;
import com.bank.performance.core.annotations.AuthUserId;
import com.bank.performance.core.exception.SignInException;
import com.bank.performance.core.util.RegexUtil;
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
public class RoleIdMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private Logger logger = LoggerFactory.getLogger(RoleIdMethodArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(Integer.class) && methodParameter.hasParameterAnnotation(AuthRoleId.class);
    }

    @Override
    public Integer resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        Object roleId = nativeWebRequest.getAttribute("roleId", RequestAttributes.SCOPE_REQUEST);
        if (RegexUtil.checkObjectIsNull(roleId)) {
            throw new SignInException("登录信息不能为空");
        }
        try {
            return (Integer) roleId;
        } catch (Exception e) {
            throw new SignInException("登录信息错误");
        }
    }
}