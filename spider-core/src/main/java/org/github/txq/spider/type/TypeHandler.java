package org.github.txq.spider.type;


import org.github.txq.spider.MessageEnvironment;

/**
 * @author tangxinqi
 * @date 2019/11/13 16:53
 */
public interface TypeHandler<E> {

    E transform(MessageEnvironment e, E curResult);
}
