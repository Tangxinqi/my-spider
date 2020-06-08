package org.github.txq.spider;


import org.apache.commons.collections4.CollectionUtils;
import org.github.txq.spider.exception.TemplateInitializeException;
import org.github.txq.spider.jtbc.TemplateSource;
import org.github.txq.spider.registry.ImmutableRegister;
import org.github.txq.spider.type.TypeHandler;

import java.util.List;
import java.util.Map;

/**
 * @author tangxinqi
 * @date 2019/11/14 12:17
 */
public class MessageParserFactoryBuilder {

    private TemplateSource source;

    private Map<String, TypeHandler<?>> typeHandlerRegister;

    private List<String> templates;

    private boolean lazy;

    public static MessageParserFactoryBuilder custom() {
        return new MessageParserFactoryBuilder();
    }

    public MessageParserFactory build() {
        MessageParserManager messageParserManager = new PoolingMessageParserManager(new ImmutableRegister<>(typeHandlerRegister), source);
        if (CollectionUtils.isEmpty(templates) && !lazy) {
            throw new TemplateInitializeException("templates can not be empty!");
        }
        if (CollectionUtils.isNotEmpty(templates)) {
            templates.forEach(t -> messageParserManager.refresh(t, null));
        }
        return new DefaultMessageParserFactory(messageParserManager, lazy);
    }

    public MessageParserFactoryBuilder setSource(TemplateSource source) {
        this.source = source;
        return this;
    }

    public MessageParserFactoryBuilder setTypeHandlerRegister(Map<String, TypeHandler<?>> typeHandlerRegister) {
        this.typeHandlerRegister = typeHandlerRegister;
        return this;
    }

    public MessageParserFactoryBuilder setTemplates(List<String> templates) {
        this.templates = templates;
        return this;
    }

    public MessageParserFactoryBuilder setLazy(boolean lazy) {
        this.lazy = lazy;
        return this;
    }
}
