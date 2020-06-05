package org.github.txq.spider.scripting;

/**
 * @author tangxinqi
 * @date 2019/12/31 19:20
 */
public class ResultSetKey extends TplNode {

    private final String name;

    public ResultSetKey(String name) {
        this.name = name;
    }

    @Override
    public void apply(DynamicContext context) {
        DynamicContext childContext = context.stash();
        super.apply(childContext);
        // 子节点为QuoteTplNode 或者 PlainTestTplNode, 故必然有且只有一个value
        Object value = childContext.elements().get(0).getValue();
        // 5、返回节点结果
        context.append(name, value);
    }
}
