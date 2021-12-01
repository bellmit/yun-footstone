/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.redisson.cache;

import com.yunjia.footstone.redisson.client.RedissonClientAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Redisson Cache 自动配置
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/1 3:00 PM
 */
@Slf4j
@Configuration
@AutoConfigureAfter(RedissonClientAutoConfigure.class)
public class RedissonCacheAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(Cache.class)
    public Cache getCache(RedissonClient redissonClient) {
        RedissonCache redissonCache = new RedissonCache(redissonClient);
        CacheUtil.setCache(redissonCache);
        return redissonCache;
    }
}
