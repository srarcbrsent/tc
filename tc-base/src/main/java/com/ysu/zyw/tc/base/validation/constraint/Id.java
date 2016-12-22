package com.ysu.zyw.tc.base.validation.constraint;

import com.ysu.zyw.tc.base.validation.constraintvalidator.TcIdConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = TcIdConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Id {

    String message() default "不合法的主键信息";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
