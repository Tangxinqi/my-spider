package org.github.txq.spider.scripting;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.github.txq.spider.log.LogDebugger;

/**
 * @author tangxinqi
 * @date 2019/11/28 11:32
 */
@Slf4j
public class XmlKeyTplNode extends TplNode {

    private static final String KEY_VALUE_TPL = "<%s>%s</%s>";

    private final String name;

    private final Joiner joiner = Joiner.on("").useForNull("");

    public XmlKeyTplNode(String name) {
        this.name = name;
    }

    @Override
    public void apply(DynamicContext context) {
        // 1、保存父节点, 收集子节点元素
        DynamicContext childContext = context.stash();
        super.apply(childContext);
        // 2、将子节点元素值进行拼接
        String value = joiner.join(childContext.elements().stream().map(KeyValue::getValue).toArray());
        // 3、返回节点结果
        String xmlValue = String.format(KEY_VALUE_TPL, name, value, name);
        if (LogDebugger.isEnableDebug()) {
            log.info("XmlTplProvider format xml node: {}", xmlValue);
        }
        context.append(null, xmlValue);
    }
}
