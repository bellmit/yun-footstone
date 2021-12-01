/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.redisson.lock;

import java.util.concurrent.TimeUnit;
import org.redisson.api.RLock;

/**
 * <p>
 * 分布式锁静态工具类
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/30 2:06 PM
 */
public class LockUtil {

    private static DistributedLock lock;

    public static void setLock(DistributedLock lock) {
        LockUtil.lock = lock;
    }

    /**
     * 分布式锁实现
     *
     * @param lockKey Lock Key
     */
    public static boolean lock(String lockKey) {
        return lock.lock(lockKey);
    }

    /**
     * 分布式锁实现-默认等待时间
     *
     * @param lockKey Lock Key
     */
    public static boolean tryLock(String lockKey) {
        return lock.tryLock(lockKey);
    }

    /**
     * 分布式锁实现-指定获取锁等待时长
     *
     * @param lockKey  Lock Key
     * @param waitTime 获取锁等待时长
     * @param unit     时间单位
     */
    public static boolean tryLock(String lockKey, long waitTime, TimeUnit unit) {
        return tryLock(lockKey, waitTime, unit);
    }

    /**
     * 分布式锁实现
     *
     * @param lockKey Lock Key
     * @param handle  业务处理
     */
    public static void lock(String lockKey, VoidHandle handle) {
        lock.lock(lockKey, handle);
    }

    /**
     * 带返回值分布式锁实现
     *
     * @param lockKey Lock Key
     * @param handle  业务处理
     * @param <T>     返回值
     */
    public static <T> T lock(String lockKey, ReturnHandle<T> handle) {
        return lock.lock(lockKey, handle);
    }

    /**
     * 分布式锁实现-默认等待时间
     *
     * @param lockKey Lock Key
     * @param handle  业务处理
     */
    public static void tryLock(String lockKey, VoidHandle handle) {
        lock.tryLock(lockKey, handle);
    }

    /**
     * 分布式锁实现-指定获取锁等待时长
     *
     * @param lockKey  Lock Key
     * @param waitTime 获取锁等待时长
     * @param unit     时间单位
     * @param handle   业务处理
     */
    public static void tryLock(String lockKey, long waitTime, TimeUnit unit, VoidHandle handle) {
        lock.tryLock(lockKey, waitTime, unit, handle);
    }

    /**
     * 带返回值分布式锁实现-默认等待时间
     *
     * @param lockKey Lock Key
     * @param handle  业务处理
     * @param <T>     返回值
     */
    public static <T> T tryLock(String lockKey, ReturnHandle<T> handle) {
        return lock.tryLock(lockKey, handle);
    }

    /**
     * 分布式锁实现-指定获取锁等待时长
     *
     * @param lockKey  Lock Key
     * @param waitTime 获取锁等待时长
     * @param unit     时间单位
     * @param handle   业务处理
     */
    public static <T> T tryLock(String lockKey, long waitTime, TimeUnit unit, ReturnHandle<T> handle) {
        return lock.tryLock(lockKey, waitTime, unit, handle);
    }

    /**
     * 获取锁
     */
    public static RLock getLock(String lockKey) {
        return lock.getLock(lockKey);
    }

    /**
     * 释放锁
     */
    public static void unlock(String lockKey) {
        lock.unlock(lockKey);
    }

    /**
     * 释放锁
     */
    public static void unlock(RLock rLock) {
        lock.unlock(rLock);
    }

    /**
     * 获取锁的真实Key（增加前缀）
     */
    public static String getLockKey(String lockKey) {
        return lock.getLockKey(lockKey);
    }
}
