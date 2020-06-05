package org.github.txq.spider.scripting;


import org.github.txq.spider.reflection.MetaObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author tangxinqi
 * @date 2019/12/31 19:14
 */
public class ResultSetNode extends TplNode {

    private final Class<?> type;

    public ResultSetNode(Class<?> type) {
        this.type = type;
    }

    @Override
    public void apply(DynamicContext context) {
        DynamicContext childContext = context.stash();
        super.apply(childContext);
        Object result;
        if (type != null) {
            MetaObject object = MetaObject.forObject(type);
            for (KeyValue element : childContext.elements()) {
                object.setValue(element.getKey(), element.getValue());
            }
            result = object.getObject();
        } else {
            Map<String, Object> value = new LinkedHashMap<>();
            for (KeyValue element : childContext.elements()) {
                value.put(element.getKey(), element.getValue());
            }
            result = value;
        }
        context.append(null, result);
    }
}
