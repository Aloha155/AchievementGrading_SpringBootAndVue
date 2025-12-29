package com.bank.performance.core.configurer;


import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;


/**
 * @author yj
 * @date 2021-05-06 9:23
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<Integer> {


    @Override
    public Optional<Integer> getCurrentAuditor() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return Optional.empty();
        }
        Integer userId = (Integer) requestAttributes.getRequest().getAttribute("userId");
        if (userId != null) {
            return Optional.of(userId);
        } else {
            return Optional.empty();
        }
    }
}