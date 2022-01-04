/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.starter.spring;

import com.yunjia.footstone.auth.core.context.AuthContext;
import com.yunjia.footstone.auth.core.context.model.AuthRequest;
import com.yunjia.footstone.auth.core.context.model.AuthResponse;
import com.yunjia.footstone.auth.core.context.model.AuthStorage;
import com.yunjia.footstone.auth.servlet.model.AuthRequestForServlet;
import com.yunjia.footstone.auth.servlet.model.AuthResponseForServlet;
import com.yunjia.footstone.auth.servlet.model.AuthStorageForServlet;

/**
 * <p>
 * 上下文处理器 [ SpringMVC版本实现 ]
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 1:59 PM
 */
public class AuthContextForSpring implements AuthContext {

    /**
     * 获取当前请求的Request对象
     */
    @Override
    public AuthRequest getRequest() {
        return new AuthRequestForServlet(SpringMVCUtil.getRequest());
    }

    /**
     * 获取当前请求的Response对象
     */
    @Override
    public AuthResponse getResponse() {
        return new AuthResponseForServlet(SpringMVCUtil.getResponse());
    }

    /**
     * 获取当前请求的 [存储器] 对象
     */
    @Override
    public AuthStorage getStorage() {
        return new AuthStorageForServlet(SpringMVCUtil.getRequest());
    }

    /**
     * 校验指定路由匹配符是否可以匹配成功指定路径
     */
    @Override
    public boolean matchPath(String pattern, String path) {
        return AuthPathMatcherHolder.getPathMatcher().match(pattern, path);
    }

    /**
     * 此上下文是否有效
     */
    @Override
    public boolean isValid() {
        return SpringMVCUtil.isWeb();
    }
}
