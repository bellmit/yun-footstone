/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.contxt;

import java.util.function.Function;

/**
 * <p>
 * 上下文属性注入
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 11:09 AM
 */
public interface ContextInjector {


    /**
     * Get http header or param inject context
     *
     * @param function left: key, right: result value
     *
     * @return context
     */
    LocalContext inject(Function<String, String> function);
}
