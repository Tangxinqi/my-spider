package org.github.txq.spider.xml;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.github.txq.spider.exception.TemplateFormatException;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import java.io.StringReader;

/**
 * @author tangxinqi
 * @date 2019/11/14 15:25
 */
public class XmlParser {

    private static final EntityResolver RESOLVER = new MyEntityResolver();

    private XmlParser() {

    }

    public static Document parseMessage(String xml) {
        try {
            SAXReader reader = new SAXReader();
            return reader.read(new InputSource(new StringReader(xml)));
        } catch (Exception e) {
            throw new IllegalArgumentException("xml is not correct!", e);
        }
    }

    public static Element parseTemplate(String template) {
        try {
            SAXReader reader = new SAXReader();
            reader.setEntityResolver(RESOLVER);
            reader.setValidation(true);
            reader.setIgnoreComments(true);
            Document document = reader.read(new InputSource(new StringReader(template)));
            return document.getRootElement();
        } catch (Exception e) {
            throw new TemplateFormatException("build template error, please check it!", e);
        }
    }
}
