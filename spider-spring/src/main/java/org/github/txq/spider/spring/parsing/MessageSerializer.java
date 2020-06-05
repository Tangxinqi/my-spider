package org.github.txq.spider.spring.parsing;

/**
 * @author tangxinqi
 * @date 2019/11/15 17:09
 */
public interface MessageSerializer {

    /**
     * 序列化入口
     *
     * @param templateName 模板名称
     * @param params       序列化需要的参数
     * @return 序列化后的目标结果
     */
    <M> M serialize(String templateName, Object params);

    /**
     * 反序列化
     *
     * @param templateName 模板名称
     * @param source       源串
     * @return 序列化对象结果
     */
    <T> T deserialize(String templateName, Object source);
}
