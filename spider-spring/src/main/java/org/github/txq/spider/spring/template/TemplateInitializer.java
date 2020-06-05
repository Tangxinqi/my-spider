package org.github.txq.spider.spring.template;

import java.util.List;

/**
 * @author tangxinqi
 * @date 2020/3/22 22:47
 */
public interface TemplateInitializer {

    /**
     * @return 模板名称集合
     */
    List<String> templates();
}
