package org.github.txq.spider.scripting;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.github.txq.spider.type.TypeHandler;

import java.util.Collection;
import java.util.Collections;

/**
 * @author tangxinqi
 * @date 2019/11/26 16:40
 */
public class ArrayTplReader extends TplNode {

    private final TypeHandler<Object[]> handler;

    private final String separator;
    private final String open;
    private final String close;

    public ArrayTplReader(String nodeId, String dependOn, String separator, String open, String close, TypeHandler<Object[]> handler) {
        this.handler = handler;
        this.separator = separator == null ? "" : separator;
        this.open = open == null ? "" : open;
        this.close = close == null ? "" : close;
        setNodeId(nodeId);
        setDependOnKeys(StringUtils.isBlank(dependOn) ? Collections.emptyList() : Collections.singletonList(dependOn));
    }


    @Override
    public void apply(DynamicContext context) {
        String dependOn = CollectionUtils.isEmpty(getDependOnKeys()) ? CURRENT_VALUE : getDependOnKeys().get(0);
        Object source = context.getBindElement(dependOn);
        Object[] value;
        if (source instanceof String) {
            String val = (String) source;
            if (StringUtils.isNotBlank(open)) {
                val = StringUtils.substringAfter((String) source, open);
            }
            if (StringUtils.isNotBlank(close)) {
                val = StringUtils.substringBefore(val, close);
            }
            value = val.split(separator, -1);
        } else if (source.getClass().isArray()) {
            value = (Object[]) source;
        } else if (source instanceof Collection) {
            value = ((Collection) source).toArray();
        } else {
            throw new IllegalArgumentException("Array reader deserialize need String or Array or Collection Type, please check resource data type!");
        }
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
