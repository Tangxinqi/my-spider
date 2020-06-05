package org.github.txq.spider.reflection;

import lombok.extern.slf4j.Slf4j;
import org.github.txq.spider.reflection.invoker.Invoker;
import org.github.txq.spider.reflection.util.PropertyTokenizer;

/**
 * @author tangxinqi
 * @date 2019/12/3 19:54
 */
@Slf4j
public class BeanWrapper extends BaseWrapper {

    private final Object origObject;

    private final MetaClass metaClass;

    public BeanWrapper(Object origObject) {
        this.origObject = origObject;
        this.metaClass = MetaClass.forMetaClass(origObject.getClass());
    }

    @Override
    public Object getPropertyValue(PropertyTokenizer tokenizer) {
        Invoker invoker = metaClass.getGetInvoker(tokenizer.getName());
        if (invoker == null) {
            throw new IllegalArgumentException("class[" + origObject.getClass().getName() + "] doesn't have property[" + tokenizer.getName() + "]");
        }
        Object value = invoker.invoke(origObject, Reflector.NULL_OBJECT);
        if (tokenizer.getIndex() != null) {
            return getCollectionValue(tokenizer, value);
        }
        return value;
    }

    @Override
    public void setPropertyValue(PropertyTokenizer tokenizer, Object value) {
        if (tokenizer.getIndex() != null) {
            Object val = metaClass.getGetInvoker(tokenizer.getName()).invoke(origObject, Reflector.NULL_OBJECT);
            setCollectionValue(tokenizer, val, value);
        } else {
            metaClass.getSetInvoker(tokenizer.getName()).invoke(origObject, value);
        }
    }

    @Override
    public Class<?> getPropertyType(PropertyTokenizer tokenizer) {
        return metaClass.getType(tokenizer.getName());
    }

}
