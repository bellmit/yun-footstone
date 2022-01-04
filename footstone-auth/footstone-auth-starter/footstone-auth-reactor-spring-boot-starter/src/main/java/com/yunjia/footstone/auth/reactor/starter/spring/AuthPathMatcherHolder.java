/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.reactor.starter.spring;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * <p>
 * 路由匹配器
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 4:33 PM
 */
public class AuthPathMatcherHolder {

    /**
     * 路由匹配器
     */
    public static PathMatcher pathMatcher;

    /**
     * 获取路由匹配器
     *
     * @return 路由匹配器
     */
    public static PathMatcher getPathMatcher() {
        if(pathMatcher == null) {
            pathMatcher = new AntPathMatcher();
        }
        return pathMatcher;
    }

    /**
     * 写入路由匹配器
     *
     * @param pathMatcher 路由匹配器
     */
    public static void setPathMatcher(PathMatcher pathMatcher) {
        AuthPathMatcherHolder.pathMatcher = pathMatcher;
    }

}
