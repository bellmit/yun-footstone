/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.reactor.starter.spring;

import com.yunjia.footstone.auth.core.config.AuthConfig;
import com.yunjia.footstone.auth.core.config.AuthCookieConfig;
import com.yunjia.footstone.auth.core.config.AuthSsoConfig;
import com.yunjia.footstone.auth.core.constant.AuthConstant;
import com.yunjia.footstone.auth.core.context.AuthContext;
import com.yunjia.footstone.auth.core.context.AuthContextForThreadLocal;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 * 注册 Auth 所需要的Bean
 * Bean 的注册与注入应该分开在两个文件中，否则在某些场景下会造成循环依赖
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 4:34 PM
 */
public class AuthBeanRegister {

    /**
     * 获取Cookie配置Bean
     *
     * @return 配置对象
     */
    @Bean
    @ConfigurationProperties(prefix = AuthConstant.PROP_PREFIX_COOKIE)
    public AuthCookieConfig getAuthCookieConfig() {
        return new AuthCookieConfig();
    }

    /**
     * 获取SSO配置Bean
     *
     * @return 配置对象
     */
    @Bean
    @ConfigurationProperties(prefix = AuthConstant.PROP_PREFIX_SSO)
    public AuthSsoConfig getAuthSsoConfig() {
        return new AuthSsoConfig();
    }

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
     * 获取容器交互Bean (ThreadLocal版)
     *
     * @return 容器交互Bean (ThreadLocal版)
     */
    @Bean
    public AuthContext getAuthContext() {
        return new AuthContextForThreadLocal() {
            /**
             * 重写路由匹配方法
             */
            @Override
            public boolean matchPath(String pattern, String path) {
                return AuthPathMatcherHolder.getPathMatcher().match(pattern, path);
            }
        };
    }
}
