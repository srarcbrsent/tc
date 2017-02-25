package com.ysu.zyw.tc.base.validation.constraintvalidator;

import com.ysu.zyw.tc.base.validation.TcValidationUtils;
import com.ysu.zyw.tc.base.validation.constraint.Mobile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class TcMobileConstraintValidator implements ConstraintValidator<Mobile, String> {

    @Override
    public void initialize(Mobile constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Objects.isNull(value) || TcValidationUtils.isMobile(value);
    }

}
