package org.github.txq.spider.boot.starter.test.handler;


import org.github.txq.spider.MessageEnvironment;
import org.github.txq.spider.spring.support.TtmTypeHandler;
import org.github.txq.spider.type.TypeHandler;

/**
 * @author tangxinqi
 * @date 2020/4/11 20:13
 */
@TtmTypeHandler("TEST_TEXT_TYPE_HANDLER")
public class TestTextTypeHandler implements TypeHandler<String> {

    @Override
    public String transform(MessageEnvironment e, String curResult) {
        return "TEST_TYPE_HANDLER";
    }
}
