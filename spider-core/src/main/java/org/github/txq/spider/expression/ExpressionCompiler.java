package org.github.txq.spider.expression;

import ognl.Ognl;
import org.github.txq.spider.exception.ExpressionProcessException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangxinqi
 * @date 2019/12/10 16:55
 */
public class ExpressionCompiler {

    private static final Map<String, Expression<?>> EXPRESSION_CACHE = new HashMap<>();

    public static Expression<?> booleanExpression(String expression) {
        try {
            if (EXPRESSION_CACHE.containsKey(expression)) {
                return EXPRESSION_CACHE.get(expression);
            }
            Object exp = Ognl.parseExpression(expression);
            Expression<?> e = new BooleanExpression(exp);
            EXPRESSION_CACHE.put(expression, e);
            return e;
        } catch (Exception e) {
            throw new ExpressionProcessException("compile expression[" + expression + "] fail, please check !", e);
        }
    }

    public static Expression<?> javaExpression(String expression) {
        try {
            if (EXPRESSION_CACHE.containsKey(expression)) {
                return EXPRESSION_CACHE.get(expression);
            }
            Object exp = Ognl.parseExpression(expression);
            Expression<?> e = new JavaClassExpression(exp);
            EXPRESSION_CACHE.put(expression, e);
            return e;
        } catch (Exception e) {
            throw new ExpressionProcessException("compile expression[" + expression + "] fail, please check !", e);
        }
    }
}
