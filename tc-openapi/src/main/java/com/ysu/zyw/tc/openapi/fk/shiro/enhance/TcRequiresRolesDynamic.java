package com.ysu.zyw.tc.openapi.fk.shiro.enhance;

import org.apache.shiro.authz.annotation.Logical;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TcRequiresRolesDynamic {

    String[] value();

    Logical logical() default Logical.AND;
}
