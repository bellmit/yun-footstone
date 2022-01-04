/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.redisson.cache.spring;

import cn.hutool.core.util.StrUtil;
import com.yunjia.footstone.redisson.client.RedissonClientAutoConfigure;
import com.yunjia.footstone.redisson.common.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Spring Cache自动配置
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 8:31 PM
 */
@Slf4j
@Configuration
@AutoConfigureAfter(RedissonClientAutoConfigure.class)
@EnableConfigurationProperties(SpringCacheProperties.class)
@EnableCaching
public class SpringCacheAutoConfigure {

    @Autowired
    private ApplicationContext ctx;

    @Bean
    @ConditionalOnMissingBean(CacheManager.class)
    public CacheManager cacheManager(RedissonClient redissonClient, SpringCacheProperties springCacheProperties) {
        try {
            String configLocation = null == springCacheProperties ? CommonConstants.SPRING_CACHE_CONFIG_LOCATION
                    : springCacheProperties.getFile();
            if(StrUtil.isBlank(configLocation)) {
                configLocation = CommonConstants.SPRING_CACHE_CONFIG_LOCATION;
            }
            if(ctx.getResource(configLocation).exists()) {
                return new RedissonSpringCacheManager(redissonClient, configLocation);
            } else {
                return new RedissonSpringCacheManager(redissonClient);
            }
        } catch(Exception e) {
            return new RedissonSpringCacheManager(redissonClient);
        }
    }
}
