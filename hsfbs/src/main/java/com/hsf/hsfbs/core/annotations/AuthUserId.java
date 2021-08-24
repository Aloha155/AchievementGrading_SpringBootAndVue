package com.hsf.hsfbs.core.annotations;

import net.bytebuddy.implementation.attribute.AnnotationRetention;
import org.jboss.jandex.AnnotationTarget;

import java.lang.annotation.*;

/**
 * @author yj
 * @date 2021-04-26 11:52
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthUserId {
}
