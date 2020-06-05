package org.github.txq.spider.scripting;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.github.txq.spider.type.TypeHandler;
import org.github.txq.spider.xml.XmlParser;

import java.util.Collections;

/**
 * @author tangxinqi
 * @date 2019/11/26 16:36
 */
public class XmlTplReader extends TplNode {

    private final TypeHandler<String> typeHandler;

    public XmlTplReader(String nodeId, String dependOn, TypeHandler<String> handler) {
        setNodeId(nodeId);
        setDependOnKeys(StringUtils.isBlank(dependOn) ? Collections.emptyList() : Collections.singletonList(dependOn));
        this.typeHandler = handler;
    }

    @Override
    public void apply(DynamicContext context) {
        String dependOn = CollectionUtils.isEmpty(getDependOnKeys()) ? CURRENT_VALUE : getDependOnKeys().get(0);
        String value = (String) context.getBindElement(dependOn);
        if (typeHandler != null) {
            value = typeHandler.transform(context.forEnvironment(), value);
        }
        Document root = XmlParser.parseMessage(value);
        if (StringUtils.isNotBlank(getNodeId())) {
            context.registerElement(getNodeId(), root);
        }
        context.registerElement(CURRENT_VALUE, root);
        super.apply(context);
    }
}
