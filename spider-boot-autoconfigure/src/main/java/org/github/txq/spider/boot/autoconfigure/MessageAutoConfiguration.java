package org.github.txq.spider.boot.autoconfigure;

import org.apache.commons.lang3.StringUtils;
import org.github.txq.spider.jtbc.TemplateSource;
import org.github.txq.spider.spring.MessageSerializerFactoryBean;
import org.github.txq.spider.spring.template.TemplateInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author tangxinqi
 * @date 2020/4/4 21:29
 */
@Configuration
@EnableConfigurationProperties(MessageProperties.class)
@AutoConfigureAfter(TemplateSourceAutoConfiguration.class)
@ConditionalOnProperty(prefix = MessageProperties.TTM_PREFIX, name = "enable", havingValue = "true")
public class MessageAutoConfiguration {

    private TemplateSource templateSource;

    private TemplateInitializer initializer;

    private MessageProperties properties;

    @Autowired
    public void setTemplateSource(TemplateSource templateSource) {
        this.templateSource = templateSource;
    }

    @Autowired
    public void setInitializer(TemplateInitializer initializer) {
        this.initializer = initializer;
    }

    @Autowired
    public void setProperties(MessageProperties properties) {
        this.properties = properties;
    }

    @Bean
    public MessageSerializerFactoryBean getMessageSerializerFactory() {
        MessageSerializerFactoryBean messageSerializerFactoryBean = new MessageSerializerFactoryBean();
        messageSerializerFactoryBean.setInitializer(initializer);
        messageSerializerFactoryBean.setSource(templateSource);
        String templateStr = properties.getTemplates();
        String[] templates = StringUtils.isBlank(templateStr) ? new String[0] : templateStr.split(",", -1);
        messageSerializerFactoryBean.setTemplates(Arrays.asList(templates));
        messageSerializerFactoryBean.setEnableDebug(properties.isDebug());
        messageSerializerFactoryBean.setLazy(properties.isLazy());
        return messageSerializerFactoryBean;
    }
}

