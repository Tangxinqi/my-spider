package org.github.txq.spider.registry;

/**
 * @author tangxinqi
 * @date 2019/11/13 16:40
 */
public interface Register<E> {

    /**
     * 查找方法
     *
     * @param key 主键
     * @return 查找结果
     */
    E lookup(String key);

    /**
     * 注册元素
     *
     * @param key 主键
     * @param e   注册元素
     */
    void register(String key, E e);
}
