package com.ysu.zyw.tc.base.validation.constraint;

import com.ysu.zyw.tc.base.validation.constraintvalidator.TcRegionConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = TcRegionConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Region {

    String message() default "不合法的区域信息";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
