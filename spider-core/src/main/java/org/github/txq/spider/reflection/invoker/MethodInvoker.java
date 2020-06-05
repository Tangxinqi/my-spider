package org.github.txq.spider.reflection.invoker;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author tangxinqi
 * @date 2019/12/9 11:26
 */
@Slf4j
public class MethodInvoker implements Invoker {

    private final Method method;

    private final Class<?> type;

    public MethodInvoker(Method method) {
        this.method = method;
        if (method.getParameterTypes().length == 1) {
            this.type = method.getParameterTypes()[0];
        } else {
            this.type = method.getReturnType();
        }

    }

    @Override
    public Object invoke(Object target, Object... args) {
        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // 此处异常可忽略
            log.error("method[{}] of class[{}] invoke fail, please check it has access to process", method.getName(), target.getClass().getName(), e);
        }
        return null;
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
