package org.github.txq.spider;


import lombok.Setter;
import org.github.txq.spider.scripting.DynamicContext;
import org.github.txq.spider.scripting.KeyValue;

import java.util.List;

/**
 * @author tangxinqi
 * @date 2019/11/25 19:28
 */
public class MessageEnvironment {

    @Setter
    private DynamicContext context;

    public MessageEnvironment() {
    }

    public List<KeyValue> getChildrenElements() {
        return context.elements();
    }

    public Object getBindElement(String name) {
        return context.getBindElement(name);
    }

    public <T> T getMetaParameter() {
        return context.getMetaParameter();
    }
}
