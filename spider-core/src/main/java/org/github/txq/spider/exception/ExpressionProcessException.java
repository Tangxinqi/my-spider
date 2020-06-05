package org.github.txq.spider.exception;

/**
 * @author tangxinqi
 * @date 2020/5/8 00:34
 */
public class ExpressionProcessException extends RuntimeException {

    public ExpressionProcessException() {
        super();
    }

    public ExpressionProcessException(String message) {
        super(message);
    }

    public ExpressionProcessException(Throwable cause) {
        super(cause);
    }

    public ExpressionProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
