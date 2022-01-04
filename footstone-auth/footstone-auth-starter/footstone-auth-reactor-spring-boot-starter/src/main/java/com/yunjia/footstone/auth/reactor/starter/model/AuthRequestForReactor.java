/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.reactor.starter.model;

import cn.hutool.core.util.StrUtil;
import com.yunjia.footstone.auth.core.AuthManager;
import com.yunjia.footstone.auth.core.context.model.AuthRequest;
import com.yunjia.footstone.auth.reactor.starter.context.AuthReactorHolder;
import com.yunjia.footstone.auth.reactor.starter.context.AuthReactorSyncHolder;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

/**
 * <p>
 * Request for Reactor
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 4:14 PM
 */
public class AuthRequestForReactor implements AuthRequest {

    /**
     * 底层Request对象
     */
    protected ServerHttpRequest request;

    /**
     * 实例化
     *
     * @param request request对象
     */
    public AuthRequestForReactor(ServerHttpRequest request) {
        this.request = request;
    }

    /**
     * 获取底层源对象
     */
    @Override
    public Object getSource() {
        return request;
    }

    /**
     * 在 [请求体] 里获取一个值
     */
    @Override
    public String getParam(String name) {
        return request.getQueryParams().getFirst(name);
    }

    /**
     * 在 [请求头] 里获取一个值
     */
    @Override
    public String getHeader(String name) {
        return request.getHeaders().getFirst(name);
    }

    /**
     * 在 [Cookie作用域] 里获取一个值
     */
    @Override
    public String getCookieValue(String name) {
        HttpCookie cookie = request.getCookies().getFirst(name);
        if(cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

    /**
     * 返回当前请求path (不包括上下文名称)
     */
    @Override
    public String getRequestPath() {
        return request.getURI().getPath();
    }

    /**
     * 返回当前请求的url，例：http://xxx.com/test
     *
     * @return see note
     */
    @Override
    public String getUrl() {
        String currDomain = AuthManager.getConfig().getCurrDomain();
        if(StrUtil.isNotBlank(currDomain)) {
            return currDomain + this.getRequestPath();
        }
        return request.getURI().toString();
    }

    /**
     * 返回当前请求的类型
     */
    @Override
    public String getMethod() {
        return request.getMethodValue();
    }

    /**
     * 转发请求
     */
    @Override
    public Object forward(String path) {
        ServerWebExchange exchange = AuthReactorSyncHolder.getContext();
        WebFilterChain chain = exchange.getAttribute(AuthReactorHolder.CHAIN_KEY);

        ServerHttpRequest newRequest = request.mutate().path(path).build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

        return chain.filter(newExchange);
    }

}
