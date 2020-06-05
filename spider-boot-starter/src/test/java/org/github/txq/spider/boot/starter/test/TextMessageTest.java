package org.github.txq.spider.boot.starter.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author tangxinqi
 * @date 2020/4/10 15:46
 */
@Slf4j
@SpringBootTest
public class TextMessageTest extends BaseTest {

    @Test
    @DisplayName("Text报文格式生成测试-成功生成和解析")
    void toTextType() {
        String content = serializer.serialize("request.text.test", new HashMap<>());
        log.info("request is {}", content);
        assertEquals("test.content", content);

        Map<String, String> value = new HashMap<>();
        value.put("key1", "value1");
        value.put("key2", "value2");
        value.put("key3", "value3");

        Map<String, String> result = serializer.deserialize("response.text.test", value);
        log.info(result.get("result"));
        assertEquals("(value1,value2,value3)", result.get("result"));
    }

    @Test
    @DisplayName("Text报文格式生成测试-成功生成和解析(配置handler)")
    void toTextTypeWithHandler() {
        String content = serializer.serialize("request.text.test.handler", new HashMap<>());
        log.info("request is {}", content);
        assertEquals("TEST_TYPE_HANDLER", content);

        Map<String, String> value = new HashMap<>();
        value.put("key1", "value1");
        value.put("key2", "value2");
        value.put("key3", "value3");

        Map<String, String> result = serializer.deserialize("response.text.test.handler", value);
        log.info(result.get("result"));
        assertEquals("TEST_TYPE_HANDLER", result.get("result"));
    }

    @Test
    @DisplayName("Text报文格式生成测试-成功生成和解析(配置java脚本)")
    void toTextTypeWithJavaScript() {
        String content = serializer.serialize("request.text.test.filter", new HashMap<>());
        log.info("request is {}", content);
        assertEquals("TEST_test000000test", content);
    }
}
