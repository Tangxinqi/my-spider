package org.github.txq.spider.scripting;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.github.txq.spider.log.LogDebugger;
import org.github.txq.spider.type.TypeHandler;

/**
 * @author tangxinqi
 * @date 2019/11/26 12:22
 */
@Slf4j
public class TextTplProvider extends TplNode {

    private final TypeHandler<String> handler;

    public TextTplProvider(String nodeId, TypeHandler<String> handler, boolean ignore) {
        this.handler = handler;
        setNodeId(nodeId);
        setIgnore(ignore);
    }

    @Override
    public void apply(DynamicContext context) {
        // 1、保存父节点, 收集子节点元素
        DynamicContext childContext = context.stash();
        super.apply(childContext);
        // 2、将子节点元素值进行拼接
        String value = valueOf(childContext);
        if (LogDebugger.isEnableDebug()) {
            log.info("TextTplProvider format message: {}", value);
        }
        // 3、结果进行转换
        if (handler != null) {
            value = handler.transform(childContext.forEnvironment(), value);
            if (LogDebugger.isEnableDebug()) {
                log.info("TextTplProvider format message after typeHandler[{}]: {}", handler.getClass().getSimpleName(), value);
            }
        }
        // 4、注册结果到注册表中
        if (StringUtils.isNotBlank(getNodeId())) {
            context.registerElement(getNodeId(), value);
        }
        // 5、返回节点结果
        if (!getIgnore()) {
            context.append(null, value);
        }
    }

    private String valueOf(DynamicContext context) {
        return StringUtils.join(context.elements().stream().map(KeyValue::getValue).map(String::valueOf).toArray());
    }
}
