package com.hsf.hsfbs.core.interceptor;


import com.hsf.hsfbs.core.annotations.Auth;
import com.hsf.hsfbs.core.config.AccountConfig;
import com.hsf.hsfbs.core.exception.NotFoundException;
import com.hsf.hsfbs.core.exception.SignInException;
import com.hsf.hsfbs.core.util.JwtUtil;
import com.hsf.hsfbs.core.util.RegexUtil;
import com.hsf.hsfbs.constant.UserState;
import com.hsf.hsfbs.entity.User;
import com.hsf.hsfbs.entity.UserRole;
import com.hsf.hsfbs.service.RoleService;
import com.hsf.hsfbs.service.UserRoleService;
import com.hsf.hsfbs.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * @author yj
 * @date 2021-04-26 14:03
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private AccountConfig accountConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            try {
                if (!method.isAnnotationPresent(Auth.class)) {
                    return true;
                }
                //你还说拉了代码。。。
                Map<String, Cookie> cookieMap = readCookieMap(request);
                String token;
                if (cookieMap.containsKey("token")) {
                    token = cookieMap.get("token").getValue();
                } else {
                    token = request.getHeader("token");
                }
                Claims claims = JwtUtil.parseJWT(token, accountConfig.getAccessTokenSecretKey());
                Integer userId = (Integer) claims.get("userId");
                if (RegexUtil.checkObjectIsNull(userId)){
                    throw new SignInException("请重新登录");
                }
                User user = userService.get(userId);
                if (UserState.DISABLED.equals(user.getUserState())){
                    throw new SignInException("账号无法使用");
                }
                //这个可以解决你的数据被删掉的问题，我在那也加了异常处理
                UserRole userRole = userRoleService.getByUserId(userId);
//                List<RolePermission> rolePermissions = rolePermissionService.listByRoleId(userRole.getRoleId());
                /**
                 * 此处省略权限校验
                 */
                request.setAttribute("userId", user.getId());
                request.setAttribute("roleId", userRole.getRoleId());
                return true;
            } catch (Exception e) {
                if (method.isAnnotationPresent(Auth.class)) {
                    if (e instanceof SignInException) {
                        throw e;
                    } else {
                        throw new SignInException("请重新登录");
                    }
                }
            }
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }


    private Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        //获取所有的cookie值
        Cookie[] cookies = request.getCookies();
        if (RegexUtil.checkObjectIsNotNull(cookies)) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}