/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.contxt;

/**
 * <p>
 * ContextClear
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 10:36 AM
 */
public interface ContextClear {

    /**
     * 线程上下文清理
     */
    default void clear() {

    }
}
