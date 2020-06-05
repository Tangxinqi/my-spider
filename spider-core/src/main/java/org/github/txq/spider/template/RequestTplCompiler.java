package org.github.txq.spider.template;

import org.dom4j.Element;
import org.github.txq.spider.MessageParser;
import org.github.txq.spider.exception.TemplateFormatException;
import org.github.txq.spider.registry.Register;
import org.github.txq.spider.scripting.ArrayStringTplProvider;
import org.github.txq.spider.scripting.ArrayTplProvider;
import org.github.txq.spider.scripting.BeanKeyTplNode;
import org.github.txq.spider.scripting.BeanTplProvider;
import org.github.txq.spider.scripting.ForEachTplNode;
import org.github.txq.spider.scripting.ITplNode;
import org.github.txq.spider.scripting.JsonTplProvider;
import org.github.txq.spider.scripting.MapTplProvider;
import org.github.txq.spider.scripting.RootTplNode;
import org.github.txq.spider.scripting.TextTplProvider;
import org.github.txq.spider.scripting.XmlKeyTplNode;
import org.github.txq.spider.scripting.XmlTplProvider;
import org.github.txq.spider.type.TypeHandler;

/**
 * @author tangxinqi
 * @date 2020/3/13 18:23
 */
public class RequestTplCompiler extends BaseFormat {

    public RequestTplCompiler(Element root, Register<TypeHandler> handlerRegister) {
        this.root = root;
        this.handlerRegister = handlerRegister;
    }

    public MessageParser compile() {
        RootTplNode rootTplNode = new RootTplNode();
        // 因为dtd文件有格式校验,可不用进行判断
        rootTplNode.addChild(doFormat(root.elements().get(0)));
        return rootTplNode;
    }

    @Override
    ITplNode doFormat(Element element) {
        String name = element.getName();
        if (TEXT_TPL_TAG.equals(name)) {
            return doFormatTextNode(element);
        } else if (MAP_TPL_TAG.equals(name)) {
            return doFormatMapNode(element);
        } else if (JSON_TPL_TAG.equals(name)) {
            return doFormatJsonNode(element);
        } else if (XML_TPL_TAG.equals(name)) {
            return doFormatXmlNode(element);
        } else if (OBJECT_TPL_TAG.equals(name)) {
            return doFormatObjectNode(element);
        } else if (ARRAY_TPL_TAG.equals(name)) {
            return doFormatArrayNode(element);
        } else if (KEY_TPL_TAG.equals(name)) {
            return doFormatKeyNode(element);
        } else if (FOREACH_TPL_TAG.equals(name)) {
            return doFormatForeachNode(element);
        } else {
            throw new TemplateFormatException("unknown tag[" + name + "]");
        }
    }

    @SuppressWarnings("uncheked")
    private ITplNode doFormatTextNode(Element element) {
        String id = getNodeId(element);
        boolean ignore = getIgnoreValue(element);
        TypeHandler<String> typeHandler = getTypeHandler(element);
        String testValue = getTestValue(element);
        ITplNode textNode = new TextTplProvider(id, typeHandler, ignore);
        return makeUpNodeTree(element, testValue, textNode);
    }

    private ITplNode doFormatMapNode(Element element) {
        String id = getNodeId(element);
        String testValue = getTestValue(element);
        boolean ignore = getIgnoreValue(element);
        ITplNode mapNode = new MapTplProvider(id, ignore);
        return makeUpNodeTree(element, testValue, mapNode);
    }

    private ITplNode doFormatJsonNode(Element element) {
        String id = getNodeId(element);
        boolean ignore = getIgnoreValue(element);
        TypeHandler<String> typeHandler = getTypeHandler(element);
        String testValue = getTestValue(element);
        ITplNode jsonNode = new JsonTplProvider(id, typeHandler, ignore);
        return makeUpNodeTree(element, testValue, jsonNode);
    }

    private ITplNode doFormatXmlNode(Element element) {
        String id = getNodeId(element);
        boolean ignore = getIgnoreValue(element);
        TypeHandler<String> typeHandler = getTypeHandler(element);
        String testValue = getTestValue(element);
        ITplNode xmlNode = new XmlTplProvider(id, typeHandler, ignore);
        return makeUpNodeTree(element, testValue, xmlNode);
    }

    private ITplNode doFormatObjectNode(Element element) {
        String id = getNodeId(element);
        boolean ignore = getIgnoreValue(element);
        String className = getTypeName(element);
        Class clz;
        try {
            clz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new TemplateFormatException(e);
        }
        String testValue = getTestValue(element);
        ITplNode beanNode = new BeanTplProvider(id, clz, ignore);
        return makeUpNodeTree(element, testValue, beanNode);
    }

    private ITplNode doFormatArrayNode(Element element) {
        String id = getNodeId(element);
        boolean ignore = getIgnoreValue(element);
        String testValue = getTestValue(element);
        String dependOn = getDependOn(element);
        String separator = getSeparator(element);
        String open = getOpenChar(element);
        String close = getCloseChar(element);
        TypeHandler<Object[]> typeHandler = getTypeHandler(element);
        ITplNode arrayNode;
        if (separator == null) {
            arrayNode = new ArrayTplProvider(id, dependOn, typeHandler, ignore);
        } else {
            arrayNode = new ArrayStringTplProvider(id, dependOn, separator, open, close, typeHandler, ignore);
        }
        return makeUpNodeTree(element, testValue, arrayNode);
    }

    private ITplNode doFormatForeachNode(Element element) {
        String testValue = getTestValue(element);
        String collection = getCollectionKey(element);
        String item = getItemName(element);
        ITplNode foreachNode = new ForEachTplNode(collection, item);
        return makeUpNodeTree(element, testValue, foreachNode);
    }

    @SuppressWarnings("uncheked")
    private ITplNode doFormatKeyNode(Element element) {
        String testValue = getTestValue(element);
        String name = getKeyName(element);
        String parentName = element.getParent().getName();
        ITplNode target = XML_TPL_TAG.equals(parentName) || KEY_TPL_TAG.equals(parentName) ? new XmlKeyTplNode(name) : new BeanKeyTplNode(name);
        return makeUpNodeTree(element, testValue, target);
    }
}
