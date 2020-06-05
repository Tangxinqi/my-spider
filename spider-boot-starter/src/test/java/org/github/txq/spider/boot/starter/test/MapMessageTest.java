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
 * @date 2020/4/10 17:31
 */
@Slf4j
@SpringBootTest
public class MapMessageTest extends BaseTest {


    @Test
    @DisplayName("Map报文格式生成测试-成功生成和解析")
    void toMapType() {
        Map<String, Object> params = new HashMap<>();
        params.put("param1", 117L);
        Map<String, Object> result = serializer.serialize("request.map.test", params);
        log.info("request is {}", result);
        log.info("request.class is {}", result.getClass());
        assertEquals("mapValue1", result.get("key1"));
        assertEquals("mapValue2", result.get("key2"));
        assertEquals("mapValue3", result.get("key3"));
        assertEquals(117L, result.get("key4"));
    }
}
