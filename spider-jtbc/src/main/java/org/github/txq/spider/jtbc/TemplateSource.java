package org.github.txq.spider.jtbc;

/**
 * 模板数据源接口定义
 *
 * @author tangxinqi
 * @date 2019/11/12 19:02
 */
public interface TemplateSource {

    /**
     * 初始化方法
     */
    void init();

    /**
     * 根据名称获取模板
     *
     * @param name    模板名称
     * @param version 模板版本号
     * @return 模板信息
     */
    String getTemplate(String name, String version);

    /**
     * 模板数据源销毁时执行方法
     */
    void destroy();
}
