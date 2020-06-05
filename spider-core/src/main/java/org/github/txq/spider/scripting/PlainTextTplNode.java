package org.github.txq.spider.scripting;

import java.util.Collections;
import java.util.List;

/**
 * @author tangxinqi
 * @date 2019/11/29 15:42
 */
public class PlainTextTplNode implements ITplNode {

    private final String value;

    public PlainTextTplNode(String value) {
        this.value = value;
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
        context.append(null, value);
    }
}
