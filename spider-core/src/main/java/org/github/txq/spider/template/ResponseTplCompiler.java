package org.github.txq.spider.template;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.github.txq.spider.MessageParser;
import org.github.txq.spider.exception.TemplateFormatException;
import org.github.txq.spider.registry.Register;
import org.github.txq.spider.scripting.ArrayTplReader;
import org.github.txq.spider.scripting.BeanTplReader;
import org.github.txq.spider.scripting.ITplNode;
import org.github.txq.spider.scripting.ResultSetKey;
import org.github.txq.spider.scripting.ResultSetNode;
import org.github.txq.spider.scripting.RootTplNode;
import org.github.txq.spider.scripting.TextTplReader;
import org.github.txq.spider.scripting.ValueReader;
import org.github.txq.spider.scripting.XmlTplReader;
import org.github.txq.spider.type.TypeHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author tangxinqi
 * @date 2020/3/13 18:27
 */
public class ResponseTplCompiler extends BaseFormat {

    public ResponseTplCompiler(Element root, Register<TypeHandler> handlerRegister) {
        this.root = root;
        this.handlerRegister = handlerRegister;
    }

    public MessageParser compile() {
        RootTplNode rootTplNode = new RootTplNode();
        List<Element> elements = root.elements();
        for (Element element : elements) {
            rootTplNode.addChild(doFormat(element));
        }
        return rootTplNode;
    }

    @Override
    ITplNode doFormat(Element element) {
        String name = element.getName();
        if (TEXT_TPL_TAG.equals(name)) {
            return doFormatTextNode(element);
        } else if (XML_TPL_TAG.equals(name)) {
            return doFormatXmlNode(element);
        } else if (OBJECT_TPL_TAG.equals(name)) {
            return doFormatObjectNode(element);
        } else if (ARRAY_TPL_TAG.equals(name)) {
            return doFormatArrayNode(element);
        } else if (VALUE_TPL_TAG.equals(name)) {
            return doFormatValueNode(element);
        } else if (RESULT_SET_TPL_TAG.equals(name)) {
            return doFormatResultSetNode(element);
        } else if (RESULT_KEY_TPL_TAG.equals(name)) {
            return doFormatResultKey(element);
        } else {
            throw new TemplateFormatException("unknown tag[" + name + "]");
        }
    }

    private ITplNode doFormatTextNode(Element element) {
        String id = getNodeId(element);
        String testValue = getTestValue(element);
        String separator = getSeparator(element);
        String open = getOpenChar(element);
        String close = getCloseChar(element);
        TypeHandler<String> typeHandler = getTypeHandler(element);
        String dependOn = getDependOn(element);
        String[] dependOns = StringUtils.isBlank(dependOn) ? new String[0] : StringUtils.split(dependOn, ",");
        dependOns = Stream.of(dependOns).map(String::trim).toArray(String[]::new);
        ITplNode arrayNode = new TextTplReader(id, Arrays.asList(dependOns), separator, open, close, typeHandler);
        return makeUpNodeTree(element, testValue, arrayNode);
    }

    private ITplNode doFormatXmlNode(Element element) {
        String id = getNodeId(element);
        String testValue = getTestValue(element);
        TypeHandler<String> typeHandler = getTypeHandler(element);
        String dependOn = getDependOn(element);
        return makeUpNodeTree(element, testValue, new XmlTplReader(id, dependOn, typeHandler));
    }

    private ITplNode doFormatObjectNode(Element element) {
        String id = getNodeId(element);
        String testValue = getTestValue(element);
        String className = getTypeName(element);
        Class clz;
        try {
            clz = StringUtils.isBlank(className) ? null : Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new TemplateFormatException(e);
        }
        TypeHandler<Object> typeHandler = getTypeHandler(element);
        String dependOn = getDependOn(element);
        return makeUpNodeTree(element, testValue, new BeanTplReader(id, dependOn, clz, typeHandler));
    }

    private ITplNode doFormatArrayNode(Element element) {
        String id = getNodeId(element);
        String testValue = getTestValue(element);
        String separator = getSeparator(element);
        String open = getOpenChar(element);
        String close = getCloseChar(element);
        String dependOn = getDependOn(element);
        TypeHandler<Object[]> typeHandler = getTypeHandler(element);
        ITplNode arrayNode;
        if (separator == null) {
            arrayNode = new ArrayTplReader(id, dependOn, null, null, null, typeHandler);
        } else {
            arrayNode = new ArrayTplReader(id, dependOn, separator, open, close, typeHandler);
        }
        return makeUpNodeTree(element, testValue, arrayNode);
    }

    private ITplNode doFormatResultSetNode(Element element) {
        String className = getTypeName(element);
        Class clz;
        try {
            clz = StringUtils.isBlank(className) ? null : Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new TemplateFormatException(e);
        }
        return makeUpNodeTree(element, "", new ResultSetNode(clz));
    }

    private ITplNode doFormatResultKey(Element element) {
        String name = getKeyName(element);
        return makeUpNodeTree(element, "", new ResultSetKey(name));
    }

    private ITplNode doFormatValueNode(Element element) {
        String id = getNodeId(element);
        String path = getPath(element);
        return makeUpNodeTree(element, "", new ValueReader(id, path));
    }


}
