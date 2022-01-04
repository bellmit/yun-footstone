/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.oauth2;

import com.yunjia.footstone.auth.oauth2.config.OAuth2Config;

/**
 * <p>
 * OAuth2 模块 总控类
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/29 7:21 PM
 */
public class OAuth2Manager {

    /**
     * OAuth2 配置 Bean
     */
    private static OAuth2Config config;

    public static OAuth2Config getConfig() {
        if(config == null) {
            // 初始化默认值
            synchronized(OAuth2Manager.class) {
                if(config == null) {
                    setConfig(new OAuth2Config());
                }
            }
        }
        return config;
    }

    public static void setConfig(OAuth2Config config) {
        OAuth2Manager.config = config;
    }
}
