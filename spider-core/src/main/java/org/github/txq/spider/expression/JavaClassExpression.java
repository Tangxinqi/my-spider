package org.github.txq.spider.expression;

import ognl.Ognl;
import org.github.txq.spider.exception.ExpressionProcessException;

import java.util.Map;

/**
 * @author tangxinqi
 * @date 2020/5/4 22:46
 */
public class JavaClassExpression implements Expression<Object> {

    private final Object expression;

    public JavaClassExpression(Object expression) {
        this.expression = expression;
    }

    @Override
    public Object evaluate(Map<String, Object> metaParameter) {
        try {
            Map context = Ognl.createDefaultContext(metaParameter, ACCESS);
            return Ognl.getValue(expression, context, metaParameter);
        } catch (Exception e) {
            throw new ExpressionProcessException("JavaClassExpression process fail, please check java class or method!", e);

        }
    }
}
