package com.ysu.zyw.tc.model.validator.constraints;


import com.ysu.zyw.tc.model.validator.TcValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileConstraintValidator implements ConstraintValidator<Mobile, String> {

    @Override
    public boolean isValid(String validStr, ConstraintValidatorContext constraintContext) {
        return TcValidator.isNull(validStr) || TcValidator.isMobile(validStr);
    }

    @Override
    public void initialize(Mobile mobile) {
    }

}
