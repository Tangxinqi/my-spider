package org.github.txq.spider.boot.starter.test;

import org.github.txq.spider.spring.template.TemplateInitializer;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author tangxinqi
 * @date 2020/4/11 17:18
 */
@Component
public class TestTemplateInitializer implements TemplateInitializer {

    @Override
    public List<String> templates() {
        return Arrays.asList(
                "request.array.test",
                "response.array.test",
                "request.xml.test",
                "response.xml.test",
                "request.text.test",
                "response.text.test",
                "request.map.test",
                "request.json.test",
                "request.bean.test",
                "response.bean.test",
                "request.text.test.handler",
                "response.text.test.handler",
                "request.text.test.filter");
    }
}
