package org.github.txq.spider.scripting;

import com.google.gson.Gson;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.github.txq.spider.reflection.MetaObject;
import org.github.txq.spider.type.TypeHandler;

import java.util.Collections;
import java.util.Map;

/**
 * @author tangxinqi
 * @date 2019/11/26 16:37
 */
public class BeanTplReader extends TplNode {

    private final Class<?> type;

    private final TypeHandler<Object> handler;

    public BeanTplReader(String nodeId, String dependOn, Class<?> type, TypeHandler<Object> handler) {
        setNodeId(nodeId);
        setDependOnKeys(StringUtils.isBlank(dependOn) ? Collections.emptyList() : Collections.singletonList(dependOn));
        this.handler = handler;
        this.type = ObjectUtils.defaultIfNull(type, Map.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void apply(DynamicContext context) {
        String dependOn = CollectionUtils.isEmpty(getDependOnKeys()) ? CURRENT_VALUE : getDependOnKeys().get(0);
        Object source = context.getBindElement(dependOn);
        if (handler != null) {
            source = handler.transform(context.forEnvironment(), source);
        }
        Object value;
        if (source instanceof String) {
            value = new Gson().fromJson((String) source, type);
        } else if (source instanceof Map) {
            MetaObject metaObject = MetaObject.forObject(type);
            ((Map) source).forEach((key, val) -> metaObject.setValue((String) key, val));
            value = metaObject.getObject();
        } else {
            throw new IllegalArgumentException("bean deserialize need String or Map type, please check resource data type!");
        }
        if (StringUtils.isNotBlank(getNodeId())) {
            context.registerElement(getNodeId(), value);
        }
        context.registerElement(CURRENT_VALUE, value);
        super.apply(context);
    }
}
