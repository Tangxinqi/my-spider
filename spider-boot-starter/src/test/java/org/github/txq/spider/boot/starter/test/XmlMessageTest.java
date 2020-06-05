package org.github.txq.spider.boot.starter.test;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.github.txq.spider.xml.XmlParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author tangxinqi
 * @date 2020/4/9 22:20
 */
@Slf4j
@SpringBootTest
public class XmlMessageTest extends BaseTest {

    @Test
    @DisplayName("Xml报文格式生成测试-成功生成和解析")
    void toXmlType() {
        String xml = serializer.serialize("request.xml.test", new HashMap<>());
        log.info("request is {}", xml);
        Document document = XmlParser.parseMessage(xml);
        Element root = document.getRootElement();
        Map<String, String> result = serializer.deserialize("response.xml.test", xml);
        log.info("response is {}", result);
        assertEquals(3, result.size());
        assertEquals("xml", root.getName());
        assertEquals(result.get("test1"), root.element("test1").element("test1_test1").getTextTrim());
        assertEquals(result.get("test2"), root.element("test2").getTextTrim());
        assertEquals(result.get("test3"), root.element("test3").getTextTrim());
    }
}
