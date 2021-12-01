/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.redisson.lock;

import cn.hutool.core.util.StrUtil;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * <p>
 * Redisson Lock
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 10:44 PM
 */
@Slf4j
public class RedissonDistributedLock implements DistributedLock {

    private RedissonClient redissonClient;

    private RedissonLockProperties lockProperties;

    public RedissonDistributedLock(RedissonClient redissonClient, RedissonLockProperties lockProperties) {
        this.redissonClient = redissonClient;
        this.lockProperties = lockProperties;
    }

    /**
     * 分布式锁实现
     *
     * @param lockKey Lock Key
     */
    @Override
    public boolean lock(String lockKey) {
        try {
            RLock rLock = getLock(lockKey);
            rLock.lock();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * 分布式锁实现-默认等待时间
     *
     * @param lockKey Lock Key
     */
    @Override
    public boolean tryLock(String lockKey) {
        try {
            RLock rLock = getLock(lockKey);
            return rLock.tryLock(lockProperties.getWaitTime(), lockProperties.getTimeUnit());
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * 分布式锁实现-指定获取锁等待时长
     *
     * @param lockKey  Lock Key
     * @param waitTime 获取锁等待时长
     * @param unit     时间单位
     */
    @Override
    public boolean tryLock(String lockKey, long waitTime, TimeUnit unit) {
        try {
            RLock rLock = getLock(lockKey);
            return rLock.tryLock(waitTime, unit);
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * 分布式锁实现
     *
     * @param lockKey Lock Key
     * @param handle  业务处理
     */
    @Override
    public void lock(String lockKey, VoidHandle handle) {
        RLock rLock = getLock(lockKey);
        try {
            rLock.lock();
            handle.execute();
        } finally {
            rLock.unlock();
        }
    }

    /**
     * 带返回值分布式锁实现
     *
     * @param lockKey Lock Key
     * @param handle  业务处理
     * @param <T>     返回值
     */
    @Override
    public <T> T lock(String lockKey, ReturnHandle<T> handle) {
        RLock rLock = getLock(lockKey);
        try {
            rLock.lock();
            return handle.execute();
        } finally {
            rLock.unlock();
        }
    }

    /**
     * 分布式锁实现-默认等待时间
     *
     * @param lockKey Lock Key
     * @param handle  业务处理
     */
    @Override
    public void tryLock(String lockKey, VoidHandle handle) {
        tryLock(lockKey, lockProperties.getWaitTime(), lockProperties.getTimeUnit(), handle);
    }

    /**
     * 分布式锁实现-指定获取锁等待时长
     *
     * @param lockKey  Lock Key
     * @param waitTime 获取锁等待时长
     * @param unit     时间单位
     * @param handle   业务处理
     */
    @Override
    public void tryLock(String lockKey, long waitTime, TimeUnit unit, VoidHandle handle) {
        RLock rLock = getLock(lockKey);
        try {
            if(!rLock.tryLock(waitTime, unit)) {
                log.error("lockKey{}，获取锁失败，抛异常处理", lockKey);
                throw new RedissonLockException("lockKey[" + lockKey + "]，获取锁失败");
            }
            handle.execute();
        } catch(Exception e) {
            throw new RedissonLockException("lockKey[" + lockKey + "]，获取锁异常", e);
        } finally {
            rLock.unlock();
        }
    }

    /**
     * 带返回值分布式锁实现-默认等待时间
     *
     * @param lockKey Lock Key
     * @param handle  业务处理
     * @param <T>     返回值
     */
    @Override
    public <T> T tryLock(String lockKey, ReturnHandle<T> handle) {
        return tryLock(lockKey, lockProperties.getWaitTime(), lockProperties.getTimeUnit(), handle);
    }

    /**
     * 分布式锁实现-指定获取锁等待时长
     *
     * @param lockKey  Lock Key
     * @param waitTime 获取锁等待时长
     * @param unit     时间单位
     * @param handle   业务处理
     */
    @Override
    public <T> T tryLock(String lockKey, long waitTime, TimeUnit unit, ReturnHandle<T> handle) {
        RLock rLock = getLock(lockKey);
        try {
            if(!rLock.tryLock(waitTime, unit)) {
                log.error("lockKey{}，获取锁超时，抛异常处理", lockKey);
                throw new RedissonLockException("lockKey[" + lockKey + "]，获取锁失败");
            }
            return handle.execute();
        } catch(Exception e) {
            throw new RedissonLockException("lockKey[" + lockKey + "]，获取锁异常", e);
        } finally {
            rLock.unlock();
        }
    }

    /**
     * 获取锁
     */
    @Override
    public RLock getLock(String lockKey) {
        String actualLockKey = getLockKey(lockKey);
        return redissonClient.getLock(actualLockKey);
    }

    /**
     * 释放锁
     */
    @Override
    public void unlock(String lockKey) {
        RLock rLock = getLock(lockKey);
        if(rLock.isLocked()) {
            unlock(rLock);
        }
    }

    /**
     * 释放锁
     */
    @Override
    public void unlock(RLock rLock) {
        rLock.unlock();
    }

    /**
     * 获取锁的真实Key（增加前缀）
     */
    @Override
    public String getLockKey(String lockKey) {
        if(StrUtil.isBlank(lockKey)) {
            throw new RuntimeException("分布式锁KEY为空");
        }
        return StrUtil.join(StrUtil.COLON, lockProperties.getLockPrefix(), lockKey);
    }
}
