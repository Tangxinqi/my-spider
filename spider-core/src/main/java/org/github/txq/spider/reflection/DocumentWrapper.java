package org.github.txq.spider.reflection;

import org.dom4j.Document;
import org.github.txq.spider.reflection.util.PropertyTokenizer;

/**
 * @author tangxinqi
 * @date 2020/1/3 15:30
 */
public class DocumentWrapper extends BaseWrapper {

    private final Document document;

    public DocumentWrapper(Document document) {
        this.document = document;
    }

    @Override
    public Object getPropertyValue(PropertyTokenizer tokenizer) {
        return document.getRootElement();
    }

    @Override
    public void setPropertyValue(PropertyTokenizer tokenizer, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<?> getPropertyType(PropertyTokenizer tokenizer) {
        return document.getClass();
    }
}
