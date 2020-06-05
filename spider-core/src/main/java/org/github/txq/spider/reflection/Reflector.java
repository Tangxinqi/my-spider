package org.github.txq.spider.reflection;

import lombok.Getter;
import org.github.txq.spider.reflection.invoker.GetFieldInvoker;
import org.github.txq.spider.reflection.invoker.Invoker;
import org.github.txq.spider.reflection.invoker.MethodInvoker;
import org.github.txq.spider.reflection.invoker.SetFieldInvoker;
import org.github.txq.spider.reflection.util.PropertyNameParser;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tangxinqi
 * @date 2019/12/3 14:24
 */
public class Reflector {

    @Getter
    private final Class<?> origClass;

    public static final Object[] NULL_OBJECT = new Object[0];

    private final Map<String, Invoker> getInvokers = new HashMap<>();

    private final Map<String, Invoker> setInvokers = new HashMap<>();

    public Reflector(Class<?> clz) {
        this.origClass = clz;
        addGetAndSetInvokers(clz);
    }

    public Invoker getGetInvoker(String name) {
        return getInvokers.get(name);
    }

    public Invoker getSetInvoker(String name) {
        return setInvokers.get(name);
    }

    public Class<?> getType(String name) {
        Invoker invoker = getInvokers.get(name);
        if (invoker == null) {
            throw new IllegalArgumentException("class[" + origClass.getName() + "] doesn't have property[" + name + "]");
        }
        return invoker.getType();
    }

    private void addGetAndSetInvokers(Class<?> clz) {
        Method[] methods = clz.getMethods();
        for (Method method : methods) {
            if (PropertyNameParser.isGetter(method.getName())) {
                getInvokers.putIfAbsent(method.getName(), new MethodInvoker(method));
            }
            if (PropertyNameParser.isSetter(method.getName())) {
                setInvokers.putIfAbsent(method.getName(), new MethodInvoker(method));
            }
        }
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            int modifiers = field.getModifiers();
            if (!(Modifier.isFinal(modifiers) && Modifier.isStatic(modifiers))) {
                getInvokers.putIfAbsent(field.getName(), new GetFieldInvoker(field));
                setInvokers.putIfAbsent(field.getName(), new SetFieldInvoker(field));
            }
        }
        if (clz.getSuperclass() != null) {
            addGetAndSetInvokers(clz.getSuperclass());
        }
    }
}
