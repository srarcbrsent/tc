package com.ysu.zyw.tc.components.commons.validation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TcUpdateConstraint {

    String pattern();

    String message();

}
