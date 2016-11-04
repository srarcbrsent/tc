package com.ysu.zyw.tc.validator.constraints;


import com.ysu.zyw.tc.validator.TcValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SafeStringConstraintValidator implements ConstraintValidator<SafeString, String> {

    private int min;

    private int max;

    private boolean oneChineseAs2Char;

    @Override
    public boolean isValid(String validStr, ConstraintValidatorContext constraintContext) {
        return TcValidator.isNull(validStr) || TcValidator.isSafeString(validStr, min, max, oneChineseAs2Char);
    }

    @Override
    public void initialize(SafeString safeString) {
        this.min = safeString.min();
        this.max = safeString.max();
        this.oneChineseAs2Char = safeString.oneChineseAs2Char();
    }

}
