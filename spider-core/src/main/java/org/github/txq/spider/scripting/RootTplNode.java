package org.github.txq.spider.scripting;


import org.github.txq.spider.MessageEnvironment;
import org.github.txq.spider.MessageParser;

/**
 * 语法生成树根类，所有目标message生成解析的入口
 *
 * @author tangxinqi
 * @date 2019/11/27 17:11
 */
public class RootTplNode extends TplNode implements MessageParser {

    @Override
    @SuppressWarnings("unchecked")
    public <S, R> R getObject(S metaParameter) {
        DynamicContext dynamicContext = new DynamicContext(new MessageEnvironment(), metaParameter);
        dynamicContext.registerElement(CURRENT_VALUE, metaParameter);
        apply(dynamicContext);
        return (R) dynamicContext.elements().get(0).getValue();
    }
}
