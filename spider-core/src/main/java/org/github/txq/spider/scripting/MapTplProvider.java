package org.github.txq.spider.scripting;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.github.txq.spider.log.LogDebugger;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author tangxinqi
 * @date 2019/11/26 14:05
 */
@Slf4j
public class MapTplProvider extends TplNode {

    public MapTplProvider(String nodeId, boolean ignore) {
        setNodeId(nodeId);
        setIgnore(ignore);
    }

    @Override
    public void apply(DynamicContext context) {
        DynamicContext childContext = context.stash();
        super.apply(childContext);
        Map<String, Object> value = new LinkedHashMap<>();
        for (KeyValue element : childContext.elements()) {
            value.put(element.getKey(), element.getValue());
            if (LogDebugger.isEnableDebug()) {
                log.info("MapTplProvider format Map, put property[{}].value={}", element.getKey(), element.getValue());
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
