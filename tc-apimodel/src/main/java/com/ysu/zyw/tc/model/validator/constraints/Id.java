package com.ysu.zyw.tc.model.validator.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target( value = {ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobileConstraintValidator.class)
public @interface Id {

    String message() default "非法的identifier！";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
