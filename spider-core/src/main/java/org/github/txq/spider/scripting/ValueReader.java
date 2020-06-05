package org.github.txq.spider.scripting;


import org.github.txq.spider.reflection.MetaObject;

/**
 * @author tangxinqi
 * @date 2019/12/31 19:11
 */
public class ValueReader extends TplNode {

    private final String path;

    public ValueReader(String nodeId, String path) {
        this.path = path;
        setNodeId(nodeId);
    }

    @Override
    public void apply(DynamicContext context) {
        Object element = context.getBindElement(CURRENT_VALUE);
        MetaObject object = MetaObject.forObject(element);
        Object value = object.getValue(path);
        context.registerElement(getNodeId(), value);
    }
}
