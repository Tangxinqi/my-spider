package org.github.txq.spider.expression;

import ognl.MemberAccess;

import java.util.Map;

/**
 * @author tangxinqi
 * @date 2019/12/10 16:58
 */
public interface Expression<T> {

    MemberAccess ACCESS = new DefaultMemberAccess(true);

    /**
     * 表达式接口
     *
     * @param metaParameter 目标参数
     * @return 表达式执行结果
     */
    T evaluate(Map<String, Object> metaParameter);
}
