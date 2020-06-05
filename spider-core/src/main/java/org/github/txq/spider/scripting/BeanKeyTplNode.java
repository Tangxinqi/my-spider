package org.github.txq.spider.scripting;


import org.github.txq.spider.exception.TemplateFormatException;

/**
 * @author tangxinqi
 * @date 2019/11/28 11:29
 */
public class BeanKeyTplNode extends TplNode {

    private final String name;

    public BeanKeyTplNode(String name) {
        this.name = name;
    }

    @Override
    public void apply(DynamicContext context) {
        DynamicContext childContext = context.stash();
        super.apply(childContext);
        Object value;
        if (childContext.elements().size() == 1) {
            value = childContext.elements().get(0).getValue();
        } else {
            throw new TemplateFormatException("bean's property could not have more than one value,please check whether template is correct or not!");
        }
        context.append(name, value);
    }
}
