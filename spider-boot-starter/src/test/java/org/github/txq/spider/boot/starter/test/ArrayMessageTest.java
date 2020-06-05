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
 * @date 2020/4/11 01:14
 */
@Slf4j
@SpringBootTest
public class ArrayMessageTest extends BaseTest {

    @Test
    @DisplayName("Array报文格式生成测试-成功生成和解析")
    void toArrayType() {
        Map<String, Object> params = new HashMap<>();
        params.put("param1", "content1");
        params.put("param2", "content2");
        params.put("param3", "content3");
        params.put("param4", "content4");
        String result = serializer.serialize("request.array.test", params);
        log.info("request is {}", result);
        assertEquals("(content1|content2|content3|content4)", result);

        Map<String, String> value = serializer.deserialize("response.array.test", result);
        assertEquals(value.get("key1"), "content1");
        assertEquals(value.get("key2"), "content2");
        assertEquals(value.get("key3"), "content3");
        assertEquals(value.get("key4"), "content4");
        log.info("response is {}", value);
    }
}
