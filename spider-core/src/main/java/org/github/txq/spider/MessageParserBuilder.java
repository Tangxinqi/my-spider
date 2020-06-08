package org.github.txq.spider;


import org.dom4j.Element;
import org.github.txq.spider.exception.TemplateFormatException;
import org.github.txq.spider.registry.Register;
import org.github.txq.spider.template.RequestTplCompiler;
import org.github.txq.spider.template.ResponseTplCompiler;
import org.github.txq.spider.type.TypeHandler;
import org.github.txq.spider.xml.XmlParser;

/**
 * @author tangxinqi
 * @date 2020/3/12 23:14
 */
public class MessageParserBuilder {

    private static final String REQUEST_TPL = "template-request";

    private static final String RESPONSE_TPL = "template-response";

    public Register<TypeHandler<?>> typeHandlerRegister;

    public static MessageParserBuilder custom() {
        return new MessageParserBuilder();
    }

    public MessageParser build(String template) {
        Element root = XmlParser.parseTemplate(template);
        String rootName = root.getName();
        if (REQUEST_TPL.equals(rootName)) {
            return new RequestTplCompiler(root, typeHandlerRegister).compile();
        } else if (RESPONSE_TPL.equals(rootName)) {
            return new ResponseTplCompiler(root, typeHandlerRegister).compile();
        } else {
            throw new TemplateFormatException("unknown root element[" + rootName + "]");
        }
    }

    public MessageParserBuilder setTypeHandlerRegister(Register<TypeHandler<?>> typeHandlerRegister) {
        this.typeHandlerRegister = typeHandlerRegister;
        return this;
    }
}
