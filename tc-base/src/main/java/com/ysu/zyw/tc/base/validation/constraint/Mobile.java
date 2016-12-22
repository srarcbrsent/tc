package com.ysu.zyw.tc.base.validation.constraint;

import com.ysu.zyw.tc.base.validation.constraintvalidator.TcMobileConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = TcMobileConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Mobile {

    String message() default "不合法的电话信息";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
