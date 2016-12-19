package com.ysu.zyw.tc.components.servlet.support;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(value = {METHOD})
@Retention(value = RUNTIME)
@Documented
public @interface TcInvokeContextInitialized {

    boolean autorun() default true;

    int order() default Integer.MAX_VALUE;

}
