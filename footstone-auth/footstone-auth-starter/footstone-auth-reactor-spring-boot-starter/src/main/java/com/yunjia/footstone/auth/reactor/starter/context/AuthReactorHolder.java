/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.reactor.starter.context;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <p>
 * Reactor上下文操作 [异步]
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 4:11 PM
 */
public class AuthReactorHolder {

    /**
     * key
     */
    public static final Class<ServerWebExchange> CONTEXT_KEY = ServerWebExchange.class;

    /**
     * chain_key
     */
    public static final String CHAIN_KEY = "WEB_FILTER_CHAIN_KEY";

    /**
     * 获取上下文对象
     *
     * @return see note
     */
    public static Mono<ServerWebExchange> getContext() {
        // 从全局 Mono<Context> 获取
        return Mono.deferContextual(Mono::just).map(ctx -> ctx.get(CONTEXT_KEY));
    }

    /**
     * 获取上下文对象, 并设置到同步上下文中
     *
     * @return see note
     */
    public static Mono<ServerWebExchange> getContextAndSetSync() {
        // 从全局 Mono<Context> 获取
        return Mono.deferContextual(Mono::just).map(ctx -> {
            // 设置到sync中
            AuthReactorSyncHolder.setContext(ctx.get(CONTEXT_KEY));
            return ctx.get(CONTEXT_KEY);
        }).doFinally(r -> {
            // 从sync中清除
            AuthReactorSyncHolder.clearContext();
        });
    }

}
