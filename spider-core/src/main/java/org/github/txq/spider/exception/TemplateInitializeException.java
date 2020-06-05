package org.github.txq.spider.exception;

/**
 * @author tangxinqi
 * @date 2020/5/8 00:42
 */
public class TemplateInitializeException extends RuntimeException {

    public TemplateInitializeException() {
        super();
    }

    public TemplateInitializeException(String message) {
        super(message);
    }

    public TemplateInitializeException(Throwable cause) {
        super(cause);
    }

    public TemplateInitializeException(String message, Throwable cause) {
        super(message, cause);
    }
}
