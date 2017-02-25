package com.ysu.zyw.tc.base.validation.constraintvalidator;

import com.ysu.zyw.tc.base.validation.TcValidationUtils;
import com.ysu.zyw.tc.base.validation.constraint.Region;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class TcRegionConstraintValidator implements ConstraintValidator<Region, String> {

    @Override
    public void initialize(Region constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Objects.isNull(value) || TcValidationUtils.isRegion(value);
    }

}
