package com.ysu.zyw.tc.base.validation.constraintvalidator;

import com.ysu.zyw.tc.base.validation.TcSpelExpressionProvider;
import com.ysu.zyw.tc.base.validation.constraint.Spel;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class TcSpelConstraintValidator implements ConstraintValidator<Spel, String> {

    private String expression;

    @Override
    public void initialize(Spel constraintAnnotation) {
        this.expression = constraintAnnotation.expression();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Expression expression = TcSpelExpressionProvider.parseExpression(this.expression);
        return Objects.isNull(value) || expression.getValue(new StandardEvaluationContext(value), Boolean.class);
    }

}
