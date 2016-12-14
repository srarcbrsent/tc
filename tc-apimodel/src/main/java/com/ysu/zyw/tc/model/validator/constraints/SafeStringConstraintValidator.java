package com.ysu.zyw.tc.model.validator.constraints;


import com.ysu.zyw.tc.model.validator.TcValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SafeStringConstraintValidator implements ConstraintValidator<SafeString, String> {

    private int min;

    private int max;

    @Override
    public boolean isValid(String validStr, ConstraintValidatorContext constraintContext) {
        return TcValidator.isNull(validStr) || TcValidator.isSafeString(validStr, min, max);
    }

    @Override
    public void initialize(SafeString safeString) {
        this.min = safeString.min();
        this.max = safeString.max();
    }

}
