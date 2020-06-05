package org.github.txq.spider.jtbc.exception;

/**
 * @author tangxinqi
 * @date 2020/4/9 22:38
 */
public class JtbcDataSourceException extends RuntimeException {

    public JtbcDataSourceException() {
        super();
    }

    public JtbcDataSourceException(String message) {
        super(message);
    }

    public JtbcDataSourceException(Throwable cause) {
        super(cause);
    }

    public JtbcDataSourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
