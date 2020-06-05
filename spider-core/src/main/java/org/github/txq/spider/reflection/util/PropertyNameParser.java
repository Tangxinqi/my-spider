package org.github.txq.spider.reflection.util;

/**
 * @author tangxinqi
 * @date 2019/12/5 20:54
 */
public class PropertyNameParser {

    private static final String IS = "is";
    private static final String GET = "get";
    private static final String SET = "set";

    private PropertyNameParser() {
    }

    public static boolean isGetter(String name) {
        return name.startsWith(GET) || name.startsWith(IS);
    }

    public static boolean isSetter(String name) {
        return name.startsWith(SET);
    }
}
