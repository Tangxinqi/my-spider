package org.github.txq.spider.reflection;


import org.github.txq.spider.reflection.util.PropertyTokenizer;

/**
 * @author tangxinqi
 * @date 2019/12/3 19:55
 */
public class CollectionWrapper extends BaseWrapper {

    private final Object collection;

    public CollectionWrapper(Object collection) {
        this.collection = collection;
    }

    @Override
    public Object getPropertyValue(PropertyTokenizer tokenizer) {
        // 当出现array.name时处于异常情况， 正确的是array[i].name等
        return getCollectionValue(tokenizer, collection);
    }

    @Override
    public void setPropertyValue(PropertyTokenizer tokenizer, Object value) {
        setCollectionValue(tokenizer, collection, value);
    }

    @Override
    public Class<?> getPropertyType(PropertyTokenizer tokenizer) {
        Object value = getCollectionValue(tokenizer, collection);
        if (value != null) {
            return value.getClass();
        }
        return Object.class;
    }
}
