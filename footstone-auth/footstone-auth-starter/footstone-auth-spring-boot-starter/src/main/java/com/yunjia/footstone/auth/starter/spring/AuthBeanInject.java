/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.starter.spring;

import com.yunjia.footstone.auth.core.AuthManager;
import com.yunjia.footstone.auth.core.basic.BasicTemplate;
import com.yunjia.footstone.auth.core.basic.BasicUtil;
import com.yunjia.footstone.auth.core.config.AuthConfig;
import com.yunjia.footstone.auth.core.context.AuthContext;
import com.yunjia.footstone.auth.core.listener.AuthListener;
import com.yunjia.footstone.auth.core.repository.AuthRepository;
import com.yunjia.footstone.auth.core.sso.SsoTemplate;
import com.yunjia.footstone.auth.core.sso.SsoUtil;
import com.yunjia.footstone.auth.core.stp.StpInterface;
import com.yunjia.footstone.auth.core.stp.StpLogic;
import com.yunjia.footstone.auth.core.stp.StpUtil;
import com.yunjia.footstone.auth.core.temp.TokenTempInterface;
import com.yunjia.footstone.auth.core.temp.TokenTempUtil;
import com.yunjia.footstone.auth.core.token.TokenTemplate;
import com.yunjia.footstone.auth.core.token.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.PathMatcher;

/**
 * <p>
 * 注入Auth所需要的Bean
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 2:06 PM
 */
public class AuthBeanInject {

    /**
     * 注入配置Bean
     *
     * @param authConfig 配置对象
     */
    @Autowired(required = false)
    public void setConfig(AuthConfig authConfig) {
        AuthManager.setConfig(authConfig);
    }

    /**
     * 注入持久化Bean
     *
     * @param authRepository AuthRepository对象
     */
    @Autowired(required = false)
    public void setAuthRepository(AuthRepository authRepository) {
        AuthManager.setAuthRepository(authRepository);
    }

    /**
     * 注入权限认证Bean
     *
     * @param stpInterface StpInterface对象
     */
    @Autowired(required = false)
    public void setStpInterface(StpInterface stpInterface) {
        AuthManager.setStpInterface(stpInterface);
    }

    /**
     * 注入上下文Bean
     *
     * @param authContext AuthContext对象
     */
    @Autowired(required = false)
    public void setAuthContext(AuthContext authContext) {
        AuthManager.setAuthContext(authContext);
    }

    /**
     * 注入侦听器Bean
     *
     * @param saTokenListener saTokenListener对象
     */
    @Autowired(required = false)
    public void setAuthListener(AuthListener saTokenListener) {
        AuthManager.setAuthListener(saTokenListener);
    }

    /**
     * 注入临时令牌验证模块 Bean
     *
     * @param tokenTempInterface TokenTempInterface对象
     */
    @Autowired(required = false)
    public void setTokenTemp(TokenTempInterface tokenTempInterface) {
        AuthManager.setTokenTemp(tokenTempInterface);
    }

    /**
     * 注入 Token 模块 Bean
     *
     * @param tokenTemplate TokenTemplate对象
     */
    @Autowired(required = false)
    public void setTokenTemplate(TokenTemplate tokenTemplate) {
        TokenUtil.tokenTemplate = tokenTemplate;
    }

    /**
     * 注入 Http Basic 认证模块
     *
     * @param basicTemplate BasicTemplate对象
     */
    @Autowired(required = false)
    public void setBasicTemplate(BasicTemplate basicTemplate) {
        BasicUtil.basicTemplate = basicTemplate;
    }

    /**
     * 注入 SSO 单点登录模块 Bean
     *
     * @param ssoTemplate SsoTemplate对象
     */
    @Autowired(required = false)
    public void setSsoTemplate(SsoTemplate ssoTemplate) {
        SsoUtil.ssoTemplate = ssoTemplate;
    }

    /**
     * 注入自定义的 StpLogic
     *
     * @param stpLogic /
     */
    @Autowired(required = false)
    public void setStpLogic(StpLogic stpLogic) {
        StpUtil.setStpLogic(stpLogic);
    }

    /**
     * 利用自动注入特性，获取Spring框架内部使用的路由匹配器
     *
     * @param pathMatcher 要设置的 pathMatcher
     */
    @Autowired(required = false)
    @Qualifier("mvcPathMatcher")
    public void setPathMatcher(PathMatcher pathMatcher) {
        AuthPathMatcherHolder.setPathMatcher(pathMatcher);
    }

}
