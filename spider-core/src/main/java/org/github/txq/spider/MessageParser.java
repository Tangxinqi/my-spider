package org.github.txq.spider;

/**
 * @author tangxinqi
 * @date 2019/11/13 19:13
 */
public interface MessageParser {

    /**
     * @param metaParameter
     * @param <S>
     * @param <R>
     * @return
     */
    <S, R> R getObject(S metaParameter);
}
