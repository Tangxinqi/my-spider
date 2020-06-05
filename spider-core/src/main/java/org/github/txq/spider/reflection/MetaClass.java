package org.github.txq.spider.reflection;

import lombok.Getter;
import org.github.txq.spider.reflection.factory.ReflectorFactory;
import org.github.txq.spider.reflection.invoker.Invoker;

/**
 * @author tangxinqi
 * @date 2019/12/3 20:05
 */
public class MetaClass {

    @Getter
    private final Class<?> type;

    private final Reflector reflector;

    private MetaClass(Class<?> type) {
        this.type = type;
        this.reflector = ReflectorFactory.findForClass(type);
    }

    public static MetaClass forMetaClass(Class<?> type) {
        return new MetaClass(type);
    }

    public Invoker getGetInvoker(String name) {
        return reflector.getGetInvoker(name);
    }

    public Invoker getSetInvoker(String name) {
        return reflector.getSetInvoker(name);
    }

    public Class<?> getType(String name) {
        return reflector.getGetInvoker(name).getType();
    }
}
