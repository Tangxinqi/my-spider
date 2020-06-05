package org.github.txq.spider.spring;

import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.github.txq.spider.MessageParserFactoryBuilder;
import org.github.txq.spider.jtbc.TemplateSource;
import org.github.txq.spider.log.LogDebugger;
import org.github.txq.spider.spring.parsing.MessageSerializer;
import org.github.txq.spider.spring.parsing.SpringMessageSerializerWrapper;
import org.github.txq.spider.spring.template.TemplateInitializer;
import org.github.txq.spider.type.TypeHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author tangxinqi
 * @date 2019/11/13 17:12
 */
public class MessageSerializerFactoryBean implements ApplicationContextAware, FactoryBean<MessageSerializer> {

    @Setter
    private TemplateSource source;

    @Setter
    private List<String> templates;

    @Setter
    private TemplateInitializer initializer;

    @Setter
    private boolean enableDebug = false;

    @Setter
    private boolean lazy;

    private ApplicationContext context;

    @Override
    public MessageSerializer getObject() {
        MessageParserFactoryBuilder builder = MessageParserFactoryBuilder.custom();
        Objects.requireNonNull(source, "");
        builder.setSource(source);

        Map<String, TypeHandler> typeHandlers = context.getBeansOfType(TypeHandler.class);
        if (MapUtils.isNotEmpty(typeHandlers)) {
            builder.setTypeHandlerRegister(typeHandlers);
        }

        List<String> tpls = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(templates)) {
            tpls.addAll(templates);
        }
        if (initializer != null) {
            tpls.addAll(initializer.templates());
        }
        builder.setTemplates(tpls);
        builder.setLazy(lazy);

        if (enableDebug) {
            LogDebugger.open();
        }
        return SpringMessageSerializerWrapper.wrap(builder.build());
    }

    @Override
    public Class<?> getObjectType() {
        return SpringMessageSerializerWrapper.class;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

}
