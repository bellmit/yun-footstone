/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.function;

/**
 * <p>
 * 设定一个函数，并传入一个参数，方便在Lambda表达式下的函数式编程
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/29 3:33 PM
 */
@FunctionalInterface
public interface VoidParamFunction<T> {

    /**
     * 执行的方法
     * @param r 传入的参数
     */
    void run(T r);
}
