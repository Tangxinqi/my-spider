package org.github.txq.spider;


/**
 * 报文解析器工厂接口, 定义生产报文解析器
 *
 * @author tangxinqi
 * @date 2019/11/13 19:07
 */
public interface MessageParserFactory {

    /**
     * 创建模板对应的报文解析器{@link }
     *
     * @param tplName 模板名称
     * @return 报文解析器
     * @see
     */
    MessageParser produce(String tplName);

    /**
     * 对报文模板进行刷新 通过{@link } 进行刷新
     *
     * @param tplName 模板名称
     * @param version 模板版本号
     */
    void replace(String tplName, String version);
}
