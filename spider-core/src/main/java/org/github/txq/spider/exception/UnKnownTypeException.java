package org.github.txq.spider.exception;

/**
 * @author tangxinqi
 * @date 2020/5/8 20:20
 */
public class UnKnownTypeException extends RuntimeException {

    public UnKnownTypeException() {
        super();
    }

    public UnKnownTypeException(String message) {
        super(message);
    }

    public UnKnownTypeException(Throwable cause) {
        super(cause);
    }

    public UnKnownTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}