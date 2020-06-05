package org.github.txq.spider.registry;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author tangxinqi
 * @date 2019/11/13 16:33
 */
public final class ImmutableRegister<E> implements Register<E> {

    private final Map<String, E> pool = new HashMap<>();

    public ImmutableRegister(Map<String, E> items) {
        if (Objects.nonNull(items)) {
            pool.putAll(items);
        }
    }

    @Override
    public E lookup(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return pool.get(key);
    }

    @Override
    public void register(String key, E e) {
        throw new UnsupportedOperationException();
    }

    public static <E> Register<E> emptyRegister() {
        return new EmptyRegister<>();
    }

    private static class EmptyRegister<E> implements Register<E> {
        @Override
        public E lookup(String key) {
            return null;
        }

        @Override
        public void register(String key, E e) {
            throw new UnsupportedOperationException();
        }
    }
}
