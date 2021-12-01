/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.redisson.lock;

/**
 * <p>
 * 无返回值业务处理器
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 10:13 PM
 */
@FunctionalInterface
public interface VoidHandle {

    /**
     * 业务处理
     */
    void execute();
}
