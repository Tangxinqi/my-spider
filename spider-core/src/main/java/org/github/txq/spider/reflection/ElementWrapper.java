package org.github.txq.spider.reflection;

import org.apache.commons.collections4.CollectionUtils;
import org.dom4j.Element;
import org.github.txq.spider.reflection.util.PropertyTokenizer;

import java.util.List;

/**
 * @author tangxinqi
 * @date 2020/4/6 11:42
 */
public class ElementWrapper extends BaseWrapper {

    private final Element origObject;

    public ElementWrapper(Element element) {
        this.origObject = element;
    }

    @Override
    public Object getPropertyValue(PropertyTokenizer tokenizer) {
        Element value;
        List<Element> elements = origObject.elements(tokenizer.getName());
        if (CollectionUtils.isEmpty(elements)) {
            return null;
        }
        if (tokenizer.getIndex() != null) {
            value = (Element) getCollectionValue(tokenizer, elements);
        } else if (elements.size() > 1) {
            return elements;
        } else {
            value = elements.get(0);
        }
        if (value != null && value.isTextOnly()) {
            return value.getTextTrim();
        }
        return value;
    }

    @Override
    public void setPropertyValue(PropertyTokenizer tokenizer, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<?> getPropertyType(PropertyTokenizer tokenizer) {
        Object value = getPropertyValue(tokenizer);
        if (value != null) {
            return value.getClass();
        }
        return Object.class;
    }
}
