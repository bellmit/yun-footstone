/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.idgenerator.utils;

import org.springframework.util.Assert;

/**
 * <p>
 * EnumUtils provides the operations for {@link ValuedEnum} such as Parse, value of...
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/26 6:24 PM
 */
public abstract class EnumUtils {

    /**
     * Parse the bounded value into ValuedEnum
     *
     * @param clz
     * @param value
     * @return
     */
    public static <T extends ValuedEnum<V>, V> T parse(Class<T> clz, V value) {
        Assert.notNull(clz, "clz can not be null");
        if (value == null) {
            return null;
        }

        for (T t : clz.getEnumConstants()) {
            if (value.equals(t.value())) {
                return t;
            }
        }
        return null;
    }

    /**
     * Null-safe valueOf function
     *
     * @param <T>
     * @param enumType
     * @param name
     * @return
     */
    public static <T extends Enum<T>> T valueOf(Class<T> enumType, String name) {
        if (name == null) {
            return null;
        }

        return Enum.valueOf(enumType, name);
    }
}
