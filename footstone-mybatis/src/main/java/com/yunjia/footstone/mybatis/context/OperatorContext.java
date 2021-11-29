/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.mybatis.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * <p>
 * 用户信息上下文
 * </p>
 * @author sunkaiyun
 */
public class OperatorContext {

    private static TransmittableThreadLocal<OperatorDto> threadLocal = new TransmittableThreadLocal<>();

    public static void setOperator(OperatorDto operatorDto) {
        threadLocal.set(operatorDto);
    }

    public static OperatorDto getOperator() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }
}
