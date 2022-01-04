/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.redisson.client;

import org.redisson.config.Config;

/**
 * <p>
 * Callback interface that can be implemented by beans wishing to customize
 * the {@link org.redisson.api.RedissonClient} auto configuration
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 6:23 PM
 */
@FunctionalInterface
public interface RedissonAutoConfigureCustomizer {

    /**
     * Customize the RedissonClient configuration.
     * @param configuration the {@link Config} to customize
     */
    void customize(final Config configuration);
}
