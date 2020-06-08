package org.github.txq.spider.scripting;

import lombok.extern.slf4j.Slf4j;
import org.github.txq.spider.expression.Expression;
import org.github.txq.spider.expression.ExpressionCompiler;
import org.github.txq.spider.log.LogDebugger;

import java.util.Collections;
import java.util.List;

/**
 * @author tangxinqi
 * @date 2020/3/8 21:08
 */
@Slf4j
public class QuoteTplNode implements ITplNode {

    private final String quote;

    private final Expression<?> expression;

    private static final String FILTER_PREFIX = "@";

    /**
     * @param quoteExpression @className@methodName 或者abc
     */
    public QuoteTplNode(String quoteExpression) {
        quoteExpression = quoteExpression.trim();
        if (quoteExpression.startsWith(FILTER_PREFIX)) {
            expression = ExpressionCompiler.javaExpression(quoteExpression);
            this.quote = null;
        } else {
            this.quote = quoteExpression;
            this.expression = null;
        }
    }

    @Override
    public boolean hasChildren() {
        return false;
    }

    @Override
    public void addChild(ITplNode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ITplNode> children() {
        return Collections.emptyList();
    }

    @Override
    public void apply(DynamicContext context) {
        Object value = expression == null ? context.getBindElement(quote) : expression.evaluate(new ExpressionParameter(context));
        if (value == null && LogDebugger.isEnableDebug()) {
            log.info("data from quote[{}] is null", quote);
        }
        context.append(null, value);
    }
}
