package org.github.txq.spider.reflection.factory;


import org.github.txq.spider.reflection.Reflector;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tangxinqi
 * @date 2019/12/4 18:00
 */
public class ReflectorFactory {

    private static final Map<Class<?>, Reflector> REFLECTOR_MAP = new ConcurrentHashMap<>();

    public static Reflector findForClass(Class<?> clz) {
        Reflector reflector = REFLECTOR_MAP.get(clz);
        if (reflector == null) {
            reflector = new Reflector(clz);
            REFLECTOR_MAP.putIfAbsent(clz, reflector);
        }
        return reflector;
    }

}
