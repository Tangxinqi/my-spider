package org.github.txq.spider.exception;

/**
 * @author tangxinqi
 * @date 2020/4/11 17:09
 */
public class TemplateFormatException extends RuntimeException {

    public TemplateFormatException() {
        super();
    }

    public TemplateFormatException(String message) {
        super(message);
    }

    public TemplateFormatException(Throwable cause) {
        super(cause);
    }

    public TemplateFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
