/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.filter;

/**
 * <p>
 * 全局过滤器-异常处理策略
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 11:31 AM
 */
public interface AuthFilterErrorStrategy {

    /**
     * 执行方法
     *
     * @param e 异常对象
     *
     * @return 输出对象(请提前序列化)
     */
    Object run(Throwable e);
}
