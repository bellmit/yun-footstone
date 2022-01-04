/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.redisson.common;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 常量
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 6:05 PM
 */
public class CommonConstants {

    private CommonConstants() {

    }

    /**
     * redisson Spring配置前缀
     */
    public static final String REDISSON_PROPERTIES_PREFIX = "spring.redis.redisson";

    /**
     * redisson 缓存是否启用
     */
    public static final String REDISSON_CACHE_ENABLE = "spring.redis.redisson.enable";

    /**
     * REDISSON ENABLE
     */
    public static final String ENABLE = "true";

    public static final String REDIS_METHOD_GET_CLUSTER = "getCluster";
    public static final String REDIS_METHOD_GET_TIMEOUT = "getTimeout";
    public static final String REDIS_METHOD_TO_MILLIS = "toMillis";
    public static final String REDIS_METHOD_GET_NODES = "getNodes";
    public static final String REDIS_METHOD_IS_SSL = "isSsl";

    /**
     * Spring Cache 配置前缀
     */
    public static final String SPRING_CACHE_CONFIG_PREFIX = "spring.cache";
    /**
     * SPRING CACHE 默认配置文件
     */
    public static final String SPRING_CACHE_CONFIG_LOCATION = "classpath:spring-cache.yaml";

    /**
     * redisson Lock配置前缀
     */
    public static final String REDISSON_LOCK_PREFIX = "spring.redis.redisson.lock";
    public static final String LOCK_KEY_PREFIX = "lock";
    public static final long WAIT_TIME = 1000L;
    public static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
}
