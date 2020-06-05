package org.github.txq.spider.reflection.factory;

import org.dom4j.Document;
import org.dom4j.Element;
import org.github.txq.spider.reflection.BeanWrapper;
import org.github.txq.spider.reflection.CollectionWrapper;
import org.github.txq.spider.reflection.DocumentWrapper;
import org.github.txq.spider.reflection.ElementWrapper;
import org.github.txq.spider.reflection.MapWrapper;
import org.github.txq.spider.reflection.ObjectWrapper;

import java.util.Collection;
import java.util.Map;

/**
 * @author tangxinqi
 * @date 2019/12/3 19:52
 */
public class ObjectWrapperFactory {

    private ObjectWrapperFactory() {
    }

    @SuppressWarnings("unchecked")
    public static ObjectWrapper wrap(Object object) {
        if (object instanceof ObjectWrapper) {
            return (ObjectWrapper) object;
        } else if (object instanceof Map) {
            return new MapWrapper((Map<String, Object>) object);
        } else if (object instanceof Collection || object.getClass().isArray()) {
            return new CollectionWrapper(object);
        } else if (object instanceof Element) {
            return new ElementWrapper((Element) object);
        } else if (object instanceof Document) {
            return new DocumentWrapper((Document) object);
        } else {
            return new BeanWrapper(object);
        }
    }
}
