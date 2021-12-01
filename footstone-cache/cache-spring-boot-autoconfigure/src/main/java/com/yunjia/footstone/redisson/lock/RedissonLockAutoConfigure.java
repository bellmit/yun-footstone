/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.redisson.lock;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yunjia.footstone.redisson.client.RedissonClientAutoConfigure;
import com.yunjia.footstone.redisson.common.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Redisson Lock自动配置
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/30 9:41 AM
 */
@Slf4j
@Configuration
@AutoConfigureAfter(RedissonClientAutoConfigure.class)
@EnableConfigurationProperties(RedissonLockProperties.class)
public class RedissonLockAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(DistributedLock.class)
    public DistributedLock getDistributedLock(RedissonClient redissonClient, RedissonLockProperties lockProperties) {
        if(ObjectUtil.isNull(lockProperties)) {
            lockProperties = new RedissonLockProperties();
        } else {
            if(StrUtil.isBlank(lockProperties.getLockPrefix())) {
                lockProperties.setLockPrefix(CommonConstants.LOCK_KEY_PREFIX);
            }
            if(ObjectUtil.hasNull(lockProperties.getWaitTime(), lockProperties.getTimeUnit())) {
                lockProperties.setWaitTime(CommonConstants.WAIT_TIME);
                lockProperties.setTimeUnit(CommonConstants.TIME_UNIT);
            }
        }
        RedissonDistributedLock distributedLock = new RedissonDistributedLock(redissonClient, lockProperties);
        LockUtil.setLock(distributedLock);
        return distributedLock;
    }
}
