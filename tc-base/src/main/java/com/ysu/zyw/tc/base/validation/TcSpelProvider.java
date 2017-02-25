package com.ysu.zyw.tc.base.validation;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TcSpelProvider {

    private static final Object PARSE_EXPRESSION_LOCK = new Object();

    private static final Map<String, Expression> cachedExpressions = new HashMap<>();

    private static final ExpressionParser expressionParser = new SpelExpressionParser();

    public static Expression parseExpression(String exp) {
        Expression expression = cachedExpressions.get(exp);
        if (Objects.nonNull(expression)) {
            return expression;
        } else {
            synchronized (PARSE_EXPRESSION_LOCK) {
                Expression parsedExpression = expressionParser.parseExpression(exp);
                cachedExpressions.put(exp, parsedExpression);
                return parsedExpression;
            }
        }
    }


}
