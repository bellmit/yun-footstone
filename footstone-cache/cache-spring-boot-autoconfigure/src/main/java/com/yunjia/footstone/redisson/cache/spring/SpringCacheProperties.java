/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.redisson.cache.spring;

import com.yunjia.footstone.redisson.common.CommonConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * Spring Cache配置
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 8:26 PM
 */
@Data
@ConfigurationProperties(prefix = CommonConstants.SPRING_CACHE_CONFIG_PREFIX)
public class SpringCacheProperties {

    /**
     * 配置文件路径
     */
    private String file;
}
