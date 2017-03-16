package com.ysu.zyw.tc.base.validation;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TcSpelExpressionProvider {

    private static final Object PARSE_EXPRESSION_LOCK = new Object();

    private static final Map<String, Expression> CACHED_EXPRESSIONS = new HashMap<>();

    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    public static Expression parseExpression(String exp) {
        Expression expression = CACHED_EXPRESSIONS.get(exp);
        if (Objects.nonNull(expression)) {
            return expression;
        } else {
            synchronized (PARSE_EXPRESSION_LOCK) {
                Expression parsedExpression = EXPRESSION_PARSER.parseExpression(exp);
                CACHED_EXPRESSIONS.put(exp, parsedExpression);
                return parsedExpression;
            }
        }
    }

}
