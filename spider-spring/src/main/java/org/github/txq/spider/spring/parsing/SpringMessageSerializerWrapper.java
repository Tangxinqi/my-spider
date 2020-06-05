package org.github.txq.spider.spring.parsing;

import org.github.txq.spider.MessageParserFactory;
import org.github.txq.spider.spring.event.OnChangeTemplateEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author tangxinqi
 * @date 2019/11/13 19:43
 */
public class SpringMessageSerializerWrapper implements ApplicationListener<OnChangeTemplateEvent>, MessageSerializer {

    private final MessageParserFactory messageParserFactory;

    private SpringMessageSerializerWrapper(MessageParserFactory messageParserFactory) {
        this.messageParserFactory = messageParserFactory;
    }

    public static SpringMessageSerializerWrapper wrap(MessageParserFactory messageParserFactory) {
        return new SpringMessageSerializerWrapper(messageParserFactory);
    }

    @Override
    public void onApplicationEvent(OnChangeTemplateEvent event) {
        messageParserFactory.replace(event.getTemplateName(), event.getVersion());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <M> M serialize(String templateName, Object params) {
        return (M) messageParserFactory.produce(templateName).getObject(params);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(String templateName, Object source) {
        return (T) messageParserFactory.produce(templateName).getObject(source);
    }
}
