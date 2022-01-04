/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.redisson.lock;

import java.util.concurrent.TimeUnit;
import org.redisson.api.RLock;

/**
 * <p>
 * 分布式锁接口定义
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/30 9:46 AM
 */
public interface DistributedLock {

    /**
     * 分布式锁实现
     *
     * @param lockKey Lock Key
     */
    boolean lock(String lockKey);

    /**
     * 分布式锁实现-默认等待时间
     *
     * @param lockKey Lock Key
     */
    boolean tryLock(String lockKey);

    /**
     * 分布式锁实现-指定获取锁等待时长
     *
     * @param lockKey  Lock Key
     * @param waitTime 获取锁等待时长
     * @param unit     时间单位
     */
    boolean tryLock(String lockKey, long waitTime, TimeUnit unit);

    /**
     * 分布式锁实现
     *
     * @param lockKey Lock Key
     * @param handle  业务处理
     */
    void lock(String lockKey, VoidHandle handle);

    /**
     * 带返回值分布式锁实现
     *
     * @param lockKey Lock Key
     * @param handle  业务处理
     * @param <T>     返回值
     */
    <T> T lock(String lockKey, ReturnHandle<T> handle);

    /**
     * 分布式锁实现-默认等待时间
     *
     * @param lockKey Lock Key
     * @param handle  业务处理
     */
    void tryLock(String lockKey, VoidHandle handle);

    /**
     * 分布式锁实现-指定获取锁等待时长
     *
     * @param lockKey  Lock Key
     * @param waitTime 获取锁等待时长
     * @param unit     时间单位
     * @param handle   业务处理
     */
    void tryLock(String lockKey, long waitTime, TimeUnit unit, VoidHandle handle);

    /**
     * 带返回值分布式锁实现-默认等待时间
     *
     * @param lockKey Lock Key
     * @param handle  业务处理
     * @param <T>     返回值
     */
    <T> T tryLock(String lockKey, ReturnHandle<T> handle);

    /**
     * 分布式锁实现-指定获取锁等待时长
     *
     * @param lockKey  Lock Key
     * @param waitTime 获取锁等待时长
     * @param unit     时间单位
     * @param handle   业务处理
     */
    <T> T tryLock(String lockKey, long waitTime, TimeUnit unit, ReturnHandle<T> handle);

    /**
     * 获取锁
     */
    RLock getLock(String lockKey);

    /**
     * 释放锁
     */
    void unlock(String lockKey);

    /**
     * 释放锁
     */
    void unlock(RLock rLock);

    /**
     * 获取锁的真实Key（增加前缀）
     */
    String getLockKey(String lockKey);
}
