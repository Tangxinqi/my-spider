package org.github.txq.spider.exception;

/**
 * @author tangxinqi
 * @date 2020/4/10 19:50
 */
public class TemplateAccessException extends RuntimeException {

    public TemplateAccessException() {
        super();
    }

    public TemplateAccessException(String message) {
        super(message);
    }

    public TemplateAccessException(Throwable cause) {
        super(cause);
    }

    public TemplateAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
