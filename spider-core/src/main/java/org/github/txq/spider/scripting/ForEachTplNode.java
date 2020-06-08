package org.github.txq.spider.scripting;

import org.apache.commons.collections4.CollectionUtils;
import org.github.txq.spider.exception.TemplateFormatException;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author tangxinqi
 * @date 2019/11/26 14:28
 */
public class ForEachTplNode extends TplNode {

    private final String collection;

    private final String item;

    public ForEachTplNode(String collection, String item) {
        this.collection = collection;
        this.item = item;
    }


    @Override
    @SuppressWarnings("unchecked")
    public void apply(DynamicContext context) {
        Object items = context.getBindElement(collection);
        if (items == null) {
            throw new TemplateFormatException("foreach's items could not be null,please check whether template is correct or not!");
        }
        if (!(items instanceof Collection || items.getClass().isArray())) {
            throw new TemplateFormatException("foreach's items must be Collection or Array,please check whether template is correct or not!");
        }
        if (context.getBindElement(item) != null) {
            throw new RuntimeException();
        }
        Stream<Object> stream = items instanceof Collection ? ((Collection<Object>) items).stream() : Stream.of((Object[]) items);
        stream.forEach(t -> {
            DynamicContext childContext = context.stash();
            childContext.registerElement(item, t);
            super.apply(childContext);
            if (CollectionUtils.isNotEmpty(childContext.elements())) {
                context.append(null, childContext.elements().get(0).getValue());
            }
        });
        context.remove(item);
    }
}
