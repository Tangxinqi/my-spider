package org.github.txq.spider.boot.starter.test;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author tangxinqi
 * @date 2020/4/10 18:30
 */
@Slf4j
@SpringBootTest
public class JsonMessageTest extends BaseTest {


    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Json报文格式生成测试-成功生成和解析")
    void toJsonType() {
        Map<String, Object> params = new HashMap<>();
        params.put("param1", 117L);
        String result = serializer.serialize("request.json.test", params);
        log.info("request is {}", result);
        Map<String, Object> value = new Gson().fromJson(result, Map.class);
        assertEquals("mapValue1", value.get("key1"));
        assertEquals("mapValue2", value.get("key2"));
        assertEquals("mapValue3", value.get("key3"));
        assertEquals(117, ((Double) value.get("key4")).intValue());
    }
}
