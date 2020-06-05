package org.github.txq.spider.scripting;

import java.util.List;

/**
 * @author tangxinqi
 * @date 2019/11/22 14:57
 */
public interface ITplNode {

    /**
     * 节点生成入口
     *
     * @param context 上下文
     */
    default void apply(DynamicContext context) {
        if (hasChildren()) {
            children().forEach(t -> t.apply(context));
        }
    }

    /**
     * 是否存在子节点
     *
     * @return true存在
     */
    boolean hasChildren();

    /**
     * 添加子节点
     *
     * @param node 子节点
     */
    void addChild(ITplNode node);

    /**
     * 返回所有子节点
     *
     * @return 子节点集合
     */
    List<ITplNode> children();
}
