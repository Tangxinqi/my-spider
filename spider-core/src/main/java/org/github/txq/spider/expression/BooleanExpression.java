package org.github.txq.spider.expression;

import ognl.Ognl;
import org.github.txq.spider.exception.ExpressionProcessException;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author tangxinqi
 * @date 2019/12/27 14:54
 */
public class BooleanExpression implements Expression<Boolean> {

    private final Object expression;

    public BooleanExpression(Object expression) {
        this.expression = expression;
    }

    @Override
    public Boolean evaluate(Map<String, Object> metaParameter) {
        try {
            Object o = Ognl.getValue(expression, Ognl.createDefaultContext(metaParameter, ACCESS), metaParameter);
            if (o instanceof Boolean) {
                return (Boolean) o;
            }
            if (o instanceof Number) {
                return !new BigDecimal(String.valueOf(o)).equals(BigDecimal.ZERO);
            }
            return o != null;
        } catch (Exception e) {
            throw new ExpressionProcessException("BooleanExpression process fail, please check expression!", e);
        }
    }
}
