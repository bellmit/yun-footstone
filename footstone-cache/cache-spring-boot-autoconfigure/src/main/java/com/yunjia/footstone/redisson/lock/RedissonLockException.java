/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.redisson.lock;

import com.yunjia.footstone.common.exception.BaseException;

/**
 * <p>
 * 分布式锁异常
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 11:02 PM
 */
public class RedissonLockException extends BaseException {

    private static final long serialVersionUID = 2438576237094158986L;

    private static final int REDISSON_LOCK_ERROR_CODE = 800;

    /**
     * 构造器
     *
     * @param message 异常详细信息
     * @param cause   异常原因
     */
    public RedissonLockException(String message, Throwable cause) {
        super(REDISSON_LOCK_ERROR_CODE, message, cause);
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     */
    public RedissonLockException(String message) {
        super(REDISSON_LOCK_ERROR_CODE, message);
    }

    /**
     * 构造器
     *
     * @param cause 异常原因
     */
    public RedissonLockException(Throwable cause) {
        super(REDISSON_LOCK_ERROR_CODE, cause);
    }
}
