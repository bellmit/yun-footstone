/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.annotation;

/**
 * <p>
 * 注解鉴权的验证模式
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 3:44 PM
 */
public enum CheckMode {

    /**
     * 必须具有所有的元素
     */
    AND,

    /**
     * 只需具有其中一个元素
     */
    OR
}
