package org.github.txq.spider.boot.autoconfigure;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author tangxinqi
 * @date 2020/4/4 21:35
 */
@Data
@ConfigurationProperties(prefix = MessageProperties.TTM_PREFIX)
public class MessageProperties {

    public static final String TTM_PREFIX = "ttm";

    private String templates;

    private boolean enable = false;

    /**
     * 开启debugger模式
     */
    private boolean debug = false;

    /**
     * 是否可以懒加载模板
     */
    private boolean lazy = false;
}
