package org.github.txq.spider.boot.starter.test;

import org.github.txq.spider.spring.parsing.MessageSerializer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tangxinqi
 * @date 2020/4/9 22:21
 */
public class BaseTest {

    protected MessageSerializer serializer;

    @Autowired
    public void setSerializer(MessageSerializer serializer) {
        this.serializer = serializer;
    }
}
