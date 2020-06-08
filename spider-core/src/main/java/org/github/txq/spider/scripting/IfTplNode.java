package org.github.txq.spider.scripting;

import org.github.txq.spider.expression.Expression;
import org.github.txq.spider.expression.ExpressionCompiler;

/**
 * @author tangxinqi
 * @date 2019/11/26 14:27
 */
public class IfTplNode extends TplNode {

    private final Expression<?> expression;

    public IfTplNode(String expression) {
        this.expression = ExpressionCompiler.booleanExpression(expression);
    }

    @Override
    public void apply(DynamicContext context) {
        if (expression.evaluate(new ExpressionParameter(context)) == Boolean.TRUE) {
            super.apply(context);
        }
    }
}
