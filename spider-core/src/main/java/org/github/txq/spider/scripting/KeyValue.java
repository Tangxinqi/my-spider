package org.github.txq.spider.scripting;

import lombok.Getter;
import lombok.ToString;

/**
 * @author tangxinqi
 * @date 2019/11/28 21:07
 */
@Getter
@ToString
public final class KeyValue {

    private final String key;

    private final Object value;

    private KeyValue(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    static KeyValue of(String key, Object value) {
        return new KeyValue(key, value);
    }
}
