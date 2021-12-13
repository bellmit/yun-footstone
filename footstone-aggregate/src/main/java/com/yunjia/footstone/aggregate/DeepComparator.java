/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.aggregate;

/**
 * <p>
 * 对象深度比较
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/10 4:26 PM
 */
public interface DeepComparator {

    /**
     * 比较方法
     */
    <T> boolean deepEquals(T a, T b);
}
