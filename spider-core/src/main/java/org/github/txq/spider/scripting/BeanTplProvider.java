package org.github.txq.spider.scripting;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.github.txq.spider.log.LogDebugger;
import org.github.txq.spider.reflection.MetaObject;

/**
 * @author tangxinqi
 * @date 2019/11/26 12:23
 */
@Slf4j
public class BeanTplProvider extends TplNode {

    private final Class<?> target;

    public BeanTplProvider(String nodeId, Class<?> targetClass, boolean ignore) {
        setNodeId(nodeId);
        setIgnore(ignore);
        this.target = targetClass;
    }

    @Override
    public void apply(DynamicContext context) {
        DynamicContext childContext = context.stash();
        super.apply(childContext);
        MetaObject targetObject = MetaObject.forObject(target);
        for (KeyValue element : childContext.elements()) {
            targetObject.setValue(element.getKey(), element.getValue());
            if (LogDebugger.isEnableDebug()) {
                log.info("BeanTplProvider format Bean[{}], set property[{}].value={}", target.getSimpleName(), element.getKey(), element.getValue());
            }
        }
        if (StringUtils.isNotBlank(getNodeId())) {
            context.registerElement(getNodeId(), targetObject.getObject());
        }
        if (!getIgnore()) {
            context.append(null, targetObject.getObject());
        }
    }
}
