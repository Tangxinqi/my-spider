package org.github.txq.spider.reflection;


import org.github.txq.spider.reflection.util.PropertyTokenizer;

/**
 * @author tangxinqi
 * @date 2019/12/3 19:18
 */
public interface ObjectWrapper {

    Object getPropertyValue(PropertyTokenizer tokenizer);

    void setPropertyValue(PropertyTokenizer tokenizer, Object value);

    Class<?> getPropertyType(PropertyTokenizer tokenizer);
}
