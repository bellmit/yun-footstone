/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.redisson.lock;

import com.yunjia.footstone.redisson.common.CommonConstants;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * Redis锁配置
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/30 9:32 AM
 */
@Data
@ConfigurationProperties(prefix = CommonConstants.REDISSON_LOCK_PREFIX)
public class RedissonLockProperties {

    /**
     * Lock Key Prefix
     */
    private String lockPrefix = CommonConstants.LOCK_KEY_PREFIX;

    /**
     * 获取锁等待时间
     */
    private Long waitTime = CommonConstants.WAIT_TIME;

    /**
     * 等待时间单位
     */
    private TimeUnit timeUnit = CommonConstants.TIME_UNIT;
}
