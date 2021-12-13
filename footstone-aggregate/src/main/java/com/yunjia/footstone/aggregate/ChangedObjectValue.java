/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 已变更的值对象
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/13 2:46 PM
 */
@AllArgsConstructor
@Getter
public class ChangedObjectValue<T> {

    /**
     * 原值对象
     */
    private final T oldObjectValue;

    /**
     * 变更后的值对象
     */
    private final T newObjectValue;
}
