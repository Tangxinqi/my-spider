package org.github.txq.spider.scripting;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.github.txq.spider.log.LogDebugger;
import org.github.txq.spider.type.TypeHandler;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author tangxinqi
 * @date 2019/11/26 12:26
 */
@Slf4j
public class ArrayTplProvider extends TplNode {

    private final TypeHandler<Object[]> handler;

    public ArrayTplProvider(String nodeId, String dependOn, TypeHandler<Object[]> handler, boolean ignore) {
        setNodeId(nodeId);
        setIgnore(ignore);
        this.handler = handler;
        setDependOnKeys(StringUtils.isBlank(dependOn) ? Collections.emptyList() : Collections.singletonList(dependOn));
    }

    @Override
    public void apply(DynamicContext context) {
        DynamicContext childContext = context.stash();
        super.apply(childContext);
        Object[] value;
        if (CollectionUtils.isNotEmpty(getDependOnKeys())) {
            value = (Object[]) context.getBindElement(getDependOnKeys().get(0));
        } else {
            value = childContext.elements().stream().map(KeyValue::getValue).toArray();
        }
        if (LogDebugger.isEnableDebug()) {
            log.info("ArrayTplProvider format message: {}", Arrays.toString(value));
        }
        if (handler != null) {
            value = handler.transform(childContext.forEnvironment(), value);
            if (LogDebugger.isEnableDebug()) {
                log.info("ArrayTplProvider format message after typeHandler[{}]: {}", handler.getClass().getSimpleName(), value);
            }
        }
        if (StringUtils.isNotBlank(getNodeId())) {
            context.registerElement(getNodeId(), value);
        }
        if (!getIgnore()) {
            context.append(null, value);
        }
    }
}
