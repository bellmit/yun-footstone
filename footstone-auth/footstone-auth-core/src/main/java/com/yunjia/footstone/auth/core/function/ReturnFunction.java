/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.function;

/**
 * <p>
 * 设定一个函数，并返回一个值，方便在Lambda表达式下的函数式编程
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 10:27 AM
 */
@FunctionalInterface
public interface ReturnFunction<T> {

    T run();
}
