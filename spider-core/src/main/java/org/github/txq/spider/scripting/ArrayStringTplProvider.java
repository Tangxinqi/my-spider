package org.github.txq.spider.scripting;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.github.txq.spider.log.LogDebugger;
import org.github.txq.spider.type.TypeHandler;

/**
 * @author tangxinqi
 * @date 2019/12/9 19:39
 */
@Slf4j
public class ArrayStringTplProvider extends ArrayTplProvider {

    private final String open;

    private final String close;

    private final Joiner joiner;


    public ArrayStringTplProvider(String nodeId, String dependOnKey, String separator, String open, String close, TypeHandler<Object[]> handler, Boolean ignore) {
        super(null, dependOnKey, handler, false);
        Joiner j = StringUtils.isBlank(separator) ? Joiner.on(StringUtils.EMPTY) : Joiner.on(separator);
        this.joiner = j.useForNull("");
        this.open = StringUtils.isBlank(open) ? StringUtils.EMPTY : open;
        this.close = StringUtils.isBlank(close) ? StringUtils.EMPTY : close;
        setNodeId(nodeId);
        setIgnore(ignore);
    }

    @Override
    public void apply(DynamicContext context) {
        DynamicContext childContext = context.stash();
        super.apply(childContext);
        Object[] element = (Object[]) childContext.elements().get(0).getValue();
        String value = open + joiner.join(element) + close;
        if (LogDebugger.isEnableDebug()) {
            log.info("ArrayStringTplProvider format message: {}", value);
        }
        if (StringUtils.isNotBlank(getNodeId())) {
            context.registerElement(getNodeId(), value);
        }
        if (!getIgnore()) {
            context.append(null, value);
        }
        context.append(null, value);
    }
}
