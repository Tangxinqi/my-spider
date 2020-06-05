package org.github.txq.spider.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tangxinqi
 * @date 2019/11/13 15:33
 */
public final class MutableRegistry<E> implements Register<E> {

    private final Map<String, E> pool;

    public MutableRegistry(boolean isThreadSafe) {
        pool = isThreadSafe ? new ConcurrentHashMap<>() : new HashMap<>();
    }

    @Override
    public E lookup(String key) {
        return pool.get(key);
    }

    @Override
    public void register(String key, E e) {
        pool.put(key, e);
    }
}
