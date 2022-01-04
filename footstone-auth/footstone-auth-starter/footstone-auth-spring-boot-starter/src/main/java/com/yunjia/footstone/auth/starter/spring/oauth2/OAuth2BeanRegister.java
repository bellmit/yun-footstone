/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.starter.spring.oauth2;

import com.yunjia.footstone.auth.core.constant.AuthConstant;
import com.yunjia.footstone.auth.oauth2.OAuth2Manager;
import com.yunjia.footstone.auth.oauth2.config.OAuth2Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 * 注册 OAuth2 所需要的Bean
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 2:18 PM
 */
@ConditionalOnClass(OAuth2Manager.class)
public class OAuth2BeanRegister {

    /**
     * 获取OAuth2配置Bean
     * @return 配置对象
     */
    @Bean
    @ConfigurationProperties(prefix = AuthConstant.PROP_PREFIX_OAUTH2)
    public OAuth2Config getOAuth2Config() {
        return new OAuth2Config();
    }

}
