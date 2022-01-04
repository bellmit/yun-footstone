/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.redisson.client;

import com.yunjia.footstone.redisson.common.CommonConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * Redisson 配置
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 6:00 PM
 */
@Data
@ConfigurationProperties(prefix = CommonConstants.REDISSON_PROPERTIES_PREFIX)
public class RedissonProperties {

    private String config;

    private String file;
}
