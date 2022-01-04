/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.function;

/**
 * <p>
 * 设定一个函数，方便在Lambda表达式下的函数式编程
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 2:34 PM
 */
@FunctionalInterface
public interface VoidFunction {

    /**
     * 执行的方法
     */
    void run();
}
