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
 * @date 2020/4/10 20:06
 */
@Slf4j
@SpringBootTest
public class BeanMessageTest extends BaseTest {

    @Test
    @DisplayName("Bean报文格式生成测试-成功生成和解析")
    void toBeanType() {
        Map<String, Object> params = new HashMap<>();
        params.put("param1", 117L);
        TestBean result = serializer.serialize("request.bean.test", params);
        log.info("request is {}", result);
        assertEquals("mapValue1", result.getKey1());
        assertEquals("mapValue2", result.getKey2());
        assertEquals("mapValue3", result.getKey3());
        assertEquals(117L, result.getKey4());

        TestBean value = serializer.deserialize("response.bean.test", new Gson().toJson(result));
        assertEquals(value.getKey1(), result.getKey1());
        assertEquals(value.getKey2(), result.getKey2());
        assertEquals(value.getKey3(), result.getKey3());
        assertEquals(value.getKey4(), result.getKey4());
        log.info("response is {}", value);
    }
}
