package com.ysu.zyw.tc.components.commons.validation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TcValid {

    String spel();

    String message();

}
