package org.github.txq.spider.boot.autoconfigure;

import org.github.txq.spider.jtbc.ClassPathResourceTemplateSource;
import org.github.txq.spider.jtbc.TemplateSource;
import org.github.txq.spider.spring.template.DefaultTemplateInitializer;
import org.github.txq.spider.spring.template.TemplateInitializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tangxinqi
 * @date 2020/4/4 23:11
 */
@Configuration
@ConditionalOnProperty(prefix = MessageProperties.TTM_PREFIX, name = "enable", havingValue = "true")
public class TemplateSourceAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public TemplateSource getTemplateSource() {
        return new ClassPathResourceTemplateSource();
    }

    @Bean
    @ConditionalOnMissingBean
    public TemplateInitializer getTemplateInitializer() {
        return new DefaultTemplateInitializer();
    }
}
