package org.github.txq.spider.scripting;

import org.apache.commons.collections4.CollectionUtils;
import org.github.txq.spider.MessageEnvironment;
import org.github.txq.spider.reflection.MetaObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tangxinqi
 * @date 2019/11/21 19:28
 */
public class DynamicContext {

    private Map<String, Object> registry;

    private MetaObject metaParameter;

    private final List<KeyValue> elements;

    protected MessageEnvironment environment;


    DynamicContext() {
        elements = new ArrayList<>();
    }

    DynamicContext(MessageEnvironment environment, Object metaObject) {
        registry = new HashMap<>();
        elements = new ArrayList<>();
        metaParameter = MetaObject.forObject(metaObject);
        this.environment = environment;
    }

    public void registerElement(String key, Object element) {
        registry.put(key, element);
    }

    public void remove(String key) {
        registry.remove(key);
    }

    public Object getBindElement(String name) {
        if (registry.containsKey(name)) {
            return registry.get(name);
        }
        return metaParameter.getValue(name);
    }

    public List<KeyValue> elements() {
        return CollectionUtils.isEmpty(elements) ? Collections.emptyList() : elements;
    }

    public void append(String key, Object element) {
        elements.add(KeyValue.of(key, element));
    }

    @SuppressWarnings("unchecked")
    public <T> T getMetaParameter() {
        return (T) metaParameter.getObject();
    }

    public DynamicContext stash() {
        return new StackedDynamicContext(this, environment);
    }

    public MessageEnvironment forEnvironment() {
        environment.setContext(this);
        return environment;
    }

    private static class StackedDynamicContext extends DynamicContext {

        private DynamicContext delegate;

        StackedDynamicContext(DynamicContext context, MessageEnvironment environment) {
            this.delegate = context;
            this.environment = environment;
        }

        @Override
        public void registerElement(String key, Object element) {
            delegate.registerElement(key, element);
        }

        @Override
        public Object getBindElement(String name) {
            return delegate.getBindElement(name);
        }

        @Override
        public void remove(String key) {
            delegate.remove(key);
        }

        @Override
        public <T> T getMetaParameter() {
            return delegate.getMetaParameter();
        }
    }

}
