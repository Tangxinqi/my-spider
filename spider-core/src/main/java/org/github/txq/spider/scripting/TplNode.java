package org.github.txq.spider.scripting;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangxinqi
 * @date 2019/11/26 20:28
 */
abstract class TplNode implements ITplNode {

    public static String CURRENT_VALUE = "$";

    private List<ITplNode> children;

    @Getter
    @Setter
    private String nodeId;

    @Getter
    @Setter
    private List<String> dependOnKeys;

    @Getter
    @Setter
    private Boolean ignore = Boolean.FALSE;

    @Override
    public boolean hasChildren() {
        return CollectionUtils.isNotEmpty(children);
    }

    @Override
    public void addChild(ITplNode node) {
        if (children == null) {
            synchronized (this) {
                if (children == null) {
                    children = new ArrayList<>();
                }
            }
        }
        children.add(node);
    }

    @Override
    public List<ITplNode> children() {
        return children;
    }
}
