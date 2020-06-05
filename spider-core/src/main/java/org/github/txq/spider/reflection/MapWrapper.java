package org.github.txq.spider.reflection;


import org.github.txq.spider.reflection.util.PropertyTokenizer;

import java.util.Map;

/**
 * @author tangxinqi
 * @date 2019/12/3 19:55
 */
public class MapWrapper extends BaseWrapper {

    private final Map<String, Object> origObject;

    public MapWrapper(Map<String, Object> origObject) {
        this.origObject = origObject;
    }

    @Override
    public Object getPropertyValue(PropertyTokenizer tokenizer) {
        Object value = origObject.get(tokenizer.getName());
        if (tokenizer.getIndex() != null) {
            return getCollectionValue(tokenizer, value);
        }
        return value;
    }

    @Override
    public void setPropertyValue(PropertyTokenizer tokenizer, Object value) {
        if (tokenizer.getIndex() != null) {
            Object val = origObject.get(tokenizer.getName());
            setCollectionValue(tokenizer, val, value);
        } else {
            origObject.put(tokenizer.getName(), value);
        }
    }

    @Override
    public Class<?> getPropertyType(PropertyTokenizer tokenizer) {
        Object value = origObject.get(tokenizer.getName());
        if (value != null) {
            return value.getClass();
        }
        return Object.class;
    }
}
