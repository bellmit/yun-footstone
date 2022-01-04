/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.filter;

/**
 * <p>
 * 全局过滤器-认证策略
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 11:29 AM
 */
public interface AuthFilterStrategy {

    /**
     * 执行方法
     *
     * @param r 无含义参数，留作扩展
     */
    void run(Object r);
}
