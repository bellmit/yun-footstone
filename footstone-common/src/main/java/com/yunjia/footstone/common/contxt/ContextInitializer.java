/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.contxt;

/**
 * <p>
 * ContextInitializer
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 10:38 AM
 */
public interface ContextInitializer {

    /**
     * 实例化系统上下文对象Context
     */
    LocalContext newInstance();
}
