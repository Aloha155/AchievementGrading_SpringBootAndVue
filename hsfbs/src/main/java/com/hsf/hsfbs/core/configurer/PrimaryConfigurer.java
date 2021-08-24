package com.hsf.hsfbs.core.configurer;


import com.hsf.hsfbs.core.interceptor.AuthInterceptor;
import com.hsf.hsfbs.core.resolver.AuthIdMethodArgumentResolver;
import com.hsf.hsfbs.core.resolver.RoleIdMethodArgumentResolver;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author yujie
 */
@Configuration
public class PrimaryConfigurer implements WebMvcConfigurer {


    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(20);
        executor.setThreadNamePrefix("demo-task-executor-");
        return executor;
    }


    @Bean
    public AuthIdMethodArgumentResolver authIdMethodArgumentResolver() {
        return new AuthIdMethodArgumentResolver();
    }

    @Bean
    public RoleIdMethodArgumentResolver roleIdMethodArgumentResolver() {
        return new RoleIdMethodArgumentResolver();
    }


    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authIdMethodArgumentResolver());
        resolvers.add(roleIdMethodArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600);
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(dateConvert());
    }

    @Bean
    public Converter<String, Date> dateConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                Date date = null;
                try {
                    date = new Date(Long.parseLong(source));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return date;
            }
        };
    }

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

}
