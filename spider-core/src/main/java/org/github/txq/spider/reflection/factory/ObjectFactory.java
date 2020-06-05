package org.github.txq.spider.reflection.factory;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author tangxinqi
 * @date 2019/12/4 14:55
 */
@Slf4j
public class ObjectFactory {

    private ObjectFactory() {

    }

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> clz) {
        try {
            clz = (Class<T>) resolveInterface(clz);
            return clz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            // 此处异常可忽略
            log.error("initialize class[{}] error, please check it has access to process!", clz.getName(), e);
        }
        return null;
    }

    private static Class<?> resolveInterface(Class<?> type) {
        Class<?> classToCreate;
        if (type == List.class || type == Collection.class || type == Iterable.class) {
            classToCreate = ArrayList.class;
        } else if (type == Map.class) {
            classToCreate = HashMap.class;
        } else if (type == SortedSet.class) {
            classToCreate = TreeSet.class;
        } else if (type == Set.class) {
            classToCreate = HashSet.class;
        } else {
            classToCreate = type;
        }
        return classToCreate;
    }
}
