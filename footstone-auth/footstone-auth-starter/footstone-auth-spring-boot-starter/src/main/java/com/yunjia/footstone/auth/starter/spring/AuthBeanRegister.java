/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.starter.spring;

import com.yunjia.footstone.auth.core.config.AuthConfig;
import com.yunjia.footstone.auth.core.constant.AuthConstant;
import com.yunjia.footstone.auth.core.context.AuthContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 * 注册Auth所需要的Bean
 * Bean 的注册与注入应该分开在两个文件中，否则在某些场景下会造成循环依赖
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 2:04 PM
 */
public class AuthBeanRegister {

    /**
     * 获取配置Bean
     *
     * @return 配置对象
     */
    @Bean
    @ConfigurationProperties(prefix = AuthConstant.PROP_PREFIX_TOKEN)
    public AuthConfig getAuthConfig() {
        return new AuthConfig();
    }

    /**
     * 获取容器交互Bean (Spring版)
     *
     * @return 容器交互Bean (Spring版)
     */
    @Bean
    public AuthContext getAuthContext() {
        return new AuthContextForSpring();
    }
}
