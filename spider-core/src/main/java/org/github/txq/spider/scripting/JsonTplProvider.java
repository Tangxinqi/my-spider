package org.github.txq.spider.scripting;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.github.txq.spider.log.LogDebugger;
import org.github.txq.spider.type.TypeHandler;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tangxinqi
 * @date 2019/11/26 14:16
 */
@Slf4j
public class JsonTplProvider extends TplNode {

    private final TypeHandler<String> handler;

    public JsonTplProvider(String nodeId, TypeHandler<String> handler, Boolean ignore) {
        setNodeId(nodeId);
        setIgnore(ignore);
        this.handler = handler;
    }

    @Override
    public void apply(DynamicContext context) {
        DynamicContext childContext = context.stash();
        super.apply(childContext);
        Map<String, Object> value = childContext.elements().stream().collect(Collectors.toMap(KeyValue::getKey, KeyValue::getValue));
        String jsonValue = new Gson().toJson(value);
        if (LogDebugger.isEnableDebug()) {
            log.info("JsonTplProvider format message:{}", jsonValue);
        }
        if (handler != null) {
            jsonValue = handler.transform(childContext.forEnvironment(), jsonValue);
            if (LogDebugger.isEnableDebug()) {
                log.info("JsonTplProvider format message after typeHandler[{}]: {}", handler.getClass().getSimpleName(), jsonValue);
            }
        }
        if (StringUtils.isNotBlank(getNodeId())) {
            context.registerElement(getNodeId(), jsonValue);
        }
        if (!getIgnore()) {
            context.append(null, jsonValue);
        }
    }
}
