/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.reactor.starter.spring.oauth2;

import com.yunjia.footstone.auth.oauth2.OAuth2Manager;
import com.yunjia.footstone.auth.oauth2.config.OAuth2Config;
import com.yunjia.footstone.auth.oauth2.core.OAuth2Template;
import com.yunjia.footstone.auth.oauth2.core.OAuth2Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

/**
 * <p>
 * 注入 OAuth2 所需要的Bean
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 2:23 PM
 */
@ConditionalOnClass(OAuth2Manager.class)
public class OAuth2BeanInject {

    /**
     * 注入OAuth2配置Bean
     *
     * @param oAuth2Config 配置对象
     */
    @Autowired(required = false)
    public void setOAuth2Config(OAuth2Config oAuth2Config) {
        OAuth2Manager.setConfig(oAuth2Config);
    }

    /**
     * 注入代码模板Bean
     *
     * @param oAuth2Template 代码模板Bean
     */
    @Autowired(required = false)
    public void setOAuth2Interface(OAuth2Template oAuth2Template) {
        OAuth2Util.oAuth2Template = oAuth2Template;
    }

}
