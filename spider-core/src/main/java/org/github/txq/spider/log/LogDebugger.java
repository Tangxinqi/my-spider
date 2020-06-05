package org.github.txq.spider.log;

/**
 * @author tangxinqi
 * @date 2020/4/10 00:32
 */
public class LogDebugger {

    private LogDebugger() {
    }

    private static boolean enableDebug = false;

    public static void open() {
        enableDebug = true;
    }

    public static boolean isEnableDebug() {
        return enableDebug;
    }
}
