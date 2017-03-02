package com.ysu.zyw.tc.base.validation.constraint;

import com.ysu.zyw.tc.base.validation.constraintvalidator.TcSpelConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = TcSpelConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Spel {

    String expression();

    String message() default "不合法的数值";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
