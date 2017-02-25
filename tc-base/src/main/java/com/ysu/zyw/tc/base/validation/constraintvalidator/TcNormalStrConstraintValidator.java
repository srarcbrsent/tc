package com.ysu.zyw.tc.base.validation.constraintvalidator;

import com.ysu.zyw.tc.base.validation.TcValidationUtils;
import com.ysu.zyw.tc.base.validation.constraint.NormalStr;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class TcNormalStrConstraintValidator implements ConstraintValidator<NormalStr, String> {

    private int min = 0;

    private int max = Integer.MAX_VALUE;

    @Override
    public void initialize(NormalStr constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Objects.isNull(value) || TcValidationUtils.isNormalStr(value, min, max);
    }

}
