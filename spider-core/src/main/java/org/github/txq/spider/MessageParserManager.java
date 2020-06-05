package org.github.txq.spider;

/**
 * @author tangxinqi
 * @date 2019/11/27 20:02
 */
public interface MessageParserManager {

    /**
     * 更新报文解析模板, 通过{@link } 进行刷新
     *
     * @param tplName 模板名称
     * @param version 模板版本
     */
    void refresh(String tplName, String version);

    /**
     * 获取一个报文解析器 {@link MessageParser}
     *
     * @param tplName 模板名称
     * @return 报文解析器
     * @see MessageParser
     */
    MessageParser request(String tplName);

}
