package org.github.txq.spider.template;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.github.txq.spider.exception.TemplateFormatException;
import org.github.txq.spider.registry.Register;
import org.github.txq.spider.scripting.ITplNode;
import org.github.txq.spider.scripting.IfTplNode;
import org.github.txq.spider.scripting.PlainTextTplNode;
import org.github.txq.spider.scripting.QuoteTplNode;
import org.github.txq.spider.type.TypeHandler;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author tangxinqi
 * @date 2020/3/18 19:22
 */
public abstract class BaseFormat {

    static final String TEXT_TPL_TAG = "template-text";
    static final String MAP_TPL_TAG = "template-map";
    static final String JSON_TPL_TAG = "template-json";
    static final String XML_TPL_TAG = "template-xml";
    static final String OBJECT_TPL_TAG = "template-object";
    static final String ARRAY_TPL_TAG = "template-array";
    static final String RESULT_SET_TPL_TAG = "result-set";
    static final String RESULT_KEY_TPL_TAG = "result-key";
    static final String KEY_TPL_TAG = "key";
    static final String FOREACH_TPL_TAG = "foreach";
    static final String VALUE_TPL_TAG = "value";

    static final String IF_TPL_ATTR = "t-if";
    static final String ID_TPL_ATTR = "id";
    static final String IGNORE_TPL_ATTR = "ignore";
    static final String TYPE_HANDLER_TPL_ATTR = "type-handler";
    static final String NAME_TPL_ATTR = "name";
    static final String TYPE_TPL_ATTR = "type";
    static final String DEPEND_ON_TPL_ATTR = "depend-on";
    static final String SEPARATOR_TPL_ATTR = "separator";
    static final String OPEN_TPL_ATTR = "open";
    static final String CLOSE_TPL_ATTR = "close";
    static final String COLLECTION_TPL_ATTR = "collection";
    static final String ITEM_TPL_ATTR = "item";
    static final String PATH_TPL_ATTR = "path";

    static final Pattern PATTERN = Pattern.compile("\\$\\{.*?}");

    Register<TypeHandler> handlerRegister;

    Element root;

    /**
     * 构造节点
     *
     * @param e 节点
     * @return 节点对象
     */
    abstract ITplNode doFormat(Element e);

    String getQuoteKey(String value) {
        return value.substring(2, value.length() - 1);
    }

    String getNodeId(Element element) {
        return getAttributeValue(element, ID_TPL_ATTR);
    }

    String getTestValue(Element element) {
        return getAttributeValue(element, IF_TPL_ATTR);
    }

    boolean getIgnoreValue(Element element) {
        String ignore = getAttributeValue(element, IGNORE_TPL_ATTR);
        return Boolean.parseBoolean(ignore);
    }

    String getKeyName(Element element) {
        return getAttributeValue(element, NAME_TPL_ATTR);
    }

    String getTypeName(Element element) {
        return getAttributeValue(element, TYPE_TPL_ATTR);
    }

    String getDependOn(Element element) {
        return getAttributeValue(element, DEPEND_ON_TPL_ATTR);
    }

    String getSeparator(Element element) {
        return getAttributeValue(element, SEPARATOR_TPL_ATTR);
    }

    String getOpenChar(Element element) {
        return getAttributeValue(element, OPEN_TPL_ATTR);
    }

    String getCloseChar(Element element) {
        return getAttributeValue(element, CLOSE_TPL_ATTR);
    }

    String getCollectionKey(Element element) {
        return getAttributeValue(element, COLLECTION_TPL_ATTR);
    }

    String getItemName(Element element) {
        return getAttributeValue(element, ITEM_TPL_ATTR);
    }

    String getPath(Element element) {
        return getAttributeValue(element, PATH_TPL_ATTR);
    }

    private String getAttributeValue(Element element, String name) {
        Attribute attribute = element.attribute(name);
        return attribute == null ? null : attribute.getValue();
    }

    @SuppressWarnings("unchecked")
    <T> TypeHandler<T> getTypeHandler(Element element) {
        String handlerName = getAttributeValue(element, TYPE_HANDLER_TPL_ATTR);
        if (StringUtils.isBlank(handlerName)) {
            return null;
        }
        TypeHandler<T> typeHandler = handlerRegister.lookup(handlerName);
        if (typeHandler == null) {
            throw new TemplateFormatException("TypeHandler[" + handlerName + "] is not exist!");
        }
        return typeHandler;
    }

    ITplNode makeUpNodeTree(Element element, String testValue, ITplNode source) {
        ITplNode node = null;
        if (StringUtils.isNotBlank(testValue)) {
            node = new IfTplNode(testValue);
        }
        if (node == null) {
            node = source;
        } else {
            node.addChild(source);
        }
        if (element.isTextOnly()) {
            String value = element.getTextTrim();
            if (StringUtils.isNotBlank(value)) {
                List<ITplNode> tplNodes = getTextValueNodes(value);
                tplNodes.forEach(source::addChild);
            }
        } else {
            String textValue = element.getTextTrim();
            if (StringUtils.isNotBlank(textValue)) {
                List<ITplNode> tplNodes = getTextValueNodes(textValue);
                tplNodes.forEach(source::addChild);
            }
            List<Element> elements = element.elements();
            for (Element e : elements) {
                source.addChild(doFormat(e));
            }
        }
        return node;
    }

    private List<ITplNode> getTextValueNodes(String value) {
        String[] values = value.trim().split("\\+", -1);
        return Stream.of(values).filter(StringUtils::isNotBlank).map(String::trim).map(this::getTextValueNode).collect(Collectors.toList());
    }

    private ITplNode getTextValueNode(String value) {
        if (isQuoteValue(value)) {
            return new QuoteTplNode(getQuoteKey(value));
        }
        return new PlainTextTplNode(value);
    }

    private boolean isQuoteValue(String value) {
        return PATTERN.matcher(value).matches();
    }
}
