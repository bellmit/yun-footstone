/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.redisson.common;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import org.redisson.api.NameMapper;

/**
 * <p>
 * 默认拼装Redis Key 前缀
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 9:03 PM
 */
public class RedissonNameMapper implements NameMapper {

    /**
     * cache key 前缀
     */
    private static String keyPrefix;

    public static void setKeyPrefix(String keyPrefix) {
        RedissonNameMapper.keyPrefix = keyPrefix;
    }

    /**
     * Applies map function to input <code>name</code>
     *
     * @param name - original Redisson object name
     *
     * @return mapped name
     */
    @Override
    public String map(String name) {
        return StrUtil.join(StrUtil.COLON, keyPrefix, name);
    }

    /**
     * Applies unmap function to input mapped <code>name</code> to get original name.
     *
     * @param name - mapped name
     *
     * @return original Redisson object name
     */
    @Override
    public String unmap(String name) {
        return CharSequenceUtil.subAfter(name, StrUtil.concat(true, keyPrefix, StrUtil.COLON), false);
    }
}
