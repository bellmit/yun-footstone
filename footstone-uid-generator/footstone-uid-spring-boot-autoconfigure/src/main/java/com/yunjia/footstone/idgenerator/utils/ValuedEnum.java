/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.idgenerator.utils;

/**
 * <p>
 * {@code ValuedEnum} defines an enumeration which is bounded to a value, you
 * may implements this interface when you defines such kind of enumeration, that
 * you can use {@link EnumUtils} to simplify parse and valueOf operation.
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/26 6:23 PM
 */
public interface ValuedEnum<T> {
    T value();
}
