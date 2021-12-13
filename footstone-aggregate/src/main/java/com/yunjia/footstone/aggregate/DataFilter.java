/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.aggregate;

/**
 * <p>
 * 数据过滤函数
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/10 6:42 PM
 */
@FunctionalInterface
public interface DataFilter<T, M> {

    /**
     * 过滤函数
     */
    M filter(T t);
}
