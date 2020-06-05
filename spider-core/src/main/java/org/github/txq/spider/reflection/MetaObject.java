package org.github.txq.spider.reflection;


import org.github.txq.spider.reflection.factory.ObjectFactory;
import org.github.txq.spider.reflection.factory.ObjectWrapperFactory;
import org.github.txq.spider.reflection.util.PropertyTokenizer;

/**
 * @author tangxinqi
 * @date 2019/11/22 15:56
 */
public class MetaObject {

    private Object origObject;

    private ObjectWrapper wrapper;

    private static final MetaObject NULL_META_OBJECT = new NullMetaObject();

    private MetaObject() {
    }

    private MetaObject(Object origObject) {
        this.origObject = origObject;
        this.wrapper = ObjectWrapperFactory.wrap(origObject);
    }

    public static MetaObject forObject(Object metaObject) {
        if (metaObject == null) {
            return NULL_META_OBJECT;
        }
        return new MetaObject(metaObject);
    }

    public static MetaObject forObject(Class<?> clz) {
        Object o = ObjectFactory.create(clz);
        return new MetaObject(o);
    }

    public void setValue(String name, Object value) {
        PropertyTokenizer tokenizer = new PropertyTokenizer(name);
        if (tokenizer.hasNext()) {
            MetaObject metaValue = metaObjectForProperty(tokenizer.getIndexName());
            if (metaValue == NULL_META_OBJECT && value != null) {
                Class<?> type = wrapper.getPropertyType(tokenizer);
                metaValue = MetaObject.forObject(type);
                metaValue.setValue(tokenizer.getChildren(), value);
                setValue(tokenizer.getIndexName(), metaValue.getObject());
            } else if (metaValue != NULL_META_OBJECT) {
                metaValue.setValue(tokenizer.getChildren(), value);
            }
        } else {
            wrapper.setPropertyValue(tokenizer, value);
        }
    }

    public Object getValue(String name) {
        PropertyTokenizer tokenizer = new PropertyTokenizer(name);
        if (tokenizer.hasNext()) {
            MetaObject metaValue = metaObjectForProperty(tokenizer.getIndexName());
            if (metaValue == NULL_META_OBJECT) {
                return null;
            } else {
                return metaValue.getValue(tokenizer.getChildren());
            }
        } else {
            return wrapper.getPropertyValue(tokenizer);
        }
    }

    public Object getObject() {
        return origObject;
    }

    private MetaObject metaObjectForProperty(String name) {
        Object value = getValue(name);
        return MetaObject.forObject(value);
    }

    private static class NullMetaObject extends MetaObject {
        @Override
        public Object getObject() {
            return null;
        }
    }
}
