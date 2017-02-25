package com.ysu.zyw.tc.base.validation.constraintvalidator;

import com.ysu.zyw.tc.base.validation.TcValidationUtils;
import com.ysu.zyw.tc.base.validation.constraint.Id;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class TcIdConstraintValidator implements ConstraintValidator<Id, String> {

    @Override
    public void initialize(Id constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Objects.isNull(value) || TcValidationUtils.isId(value);
    }

}
