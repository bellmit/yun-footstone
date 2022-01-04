/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.context.model;

/**
 * <p>
 * [存储器] 包装类
 * 在 Request作用域里: 存值、取值
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/25 9:47 AM
 */
public interface AuthStorage {

    /**
     * 获取底层源对象
     * @return see note
     */
    Object getSource();

    /**
     * 在 [Request作用域] 里写入一个值
     * @param key 键
     * @param value 值
     */
    void set(String key, Object value);

    /**
     * 在 [Request作用域] 里获取一个值
     * @param key 键
     * @return 值
     */
    Object get(String key);

    /**
     * 在 [Request作用域] 里删除一个值
     * @param key 键
     */
    void delete(String key);
}
