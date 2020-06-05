package org.github.txq.spider.reflection.invoker;

/**
 * @author tangxinqi
 * @date 2019/12/4 18:05
 */
public interface Invoker {

    Object invoke(Object target, Object... args);

    Class<?> getType();
}
