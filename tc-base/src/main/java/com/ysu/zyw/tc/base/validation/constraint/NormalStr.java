package com.ysu.zyw.tc.base.validation.constraint;

import com.ysu.zyw.tc.base.validation.constraintvalidator.TcNormalStrConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = TcNormalStrConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NormalStr {

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String message() default "不合法的字符串信息";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
