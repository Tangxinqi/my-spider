package org.github.txq.spider.reflection.invoker;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @author tangxinqi
 * @date 2019/12/4 18:08
 */
@Slf4j
public class GetFieldInvoker implements Invoker {

    private final Field field;

    public GetFieldInvoker(Field field) {
        this.field = field;
    }

    @Override
    public Object invoke(Object target, Object... args) {
        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            // 此处异常可忽略,通常情况下不会触发
            log.error("field[{}] of class[{}] invoke fail, please check it has access to process!", field.getName(), target.getClass().getName(), e);
        }
        return null;
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }
}
