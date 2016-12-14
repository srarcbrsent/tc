package com.ysu.zyw.tc.model.validator.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(value = {ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SafeStringConstraintValidator.class)
public @interface SafeString {

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String message() default "含有非法的字符串！";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
