package org.github.txq.spider.scripting;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.github.txq.spider.type.TypeHandler;

import java.util.Collections;
import java.util.List;

/**
 * @author tangxinqi
 * @date 2019/11/26 15:48
 */
public class TextTplReader extends TplNode {

    private final TypeHandler<String> handler;

    private final String separator;
    private final String open;
    private final String close;

    public TextTplReader(String nodeId, List<String> dependOns, String separator, String open, String close, TypeHandler<String> handler) {
        this.handler = handler;
        this.separator = separator == null ? "" : separator;
        this.open = open == null ? "" : open;
        this.close = close == null ? "" : close;
        setNodeId(nodeId);
        setDependOnKeys(dependOns);
    }

    @Override
    public void apply(DynamicContext context) {
        List<String> dependOns = CollectionUtils.isEmpty(getDependOnKeys()) ? Collections.singletonList(CURRENT_VALUE) : getDependOnKeys();
        String[] values = dependOns.stream().map(t -> {
            Object item = context.getBindElement(t);
            return String.valueOf(item);
        }).toArray(String[]::new);
        String value = open + StringUtils.join(values, separator) + close;
        if (handler != null) {
            value = handler.transform(context.forEnvironment(), value);
        }
        if (StringUtils.isNotBlank(getNodeId())) {
            context.registerElement(getNodeId(), value);
        }
        context.registerElement(CURRENT_VALUE, value);
        super.apply(context);
    }
}
