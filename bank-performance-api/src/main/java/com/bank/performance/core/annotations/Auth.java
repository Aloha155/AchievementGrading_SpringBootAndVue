package com.bank.performance.core.annotations;

import net.bytebuddy.implementation.attribute.AnnotationRetention;
import org.jboss.jandex.AnnotationTarget;

import java.lang.annotation.*;

/**
 * @author yj
 * @date 2021-04-26 11:59
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
}
