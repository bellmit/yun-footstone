/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.reactor.starter.context;

import com.yunjia.footstone.auth.core.context.AuthContextForThreadLocalStorage;
import com.yunjia.footstone.auth.core.context.AuthContextForThreadLocalStorage.Box;
import com.yunjia.footstone.auth.core.context.model.AuthRequest;
import com.yunjia.footstone.auth.core.context.model.AuthResponse;
import com.yunjia.footstone.auth.core.context.model.AuthStorage;
import com.yunjia.footstone.auth.core.function.VoidFunction;
import com.yunjia.footstone.auth.reactor.starter.model.AuthRequestForReactor;
import com.yunjia.footstone.auth.reactor.starter.model.AuthResponseForReactor;
import com.yunjia.footstone.auth.reactor.starter.model.AuthStorageForReactor;
import org.springframework.web.server.ServerWebExchange;

/**
 * <p>
 * Reactor上下文操作 [同步]
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 4:12 PM
 */
public class AuthReactorSyncHolder {

    /**
     * 写入上下文对象
     *
     * @param exchange see note
     */
    public static void setContext(ServerWebExchange exchange) {
        AuthRequest request = new AuthRequestForReactor(exchange.getRequest());
        AuthResponse response = new AuthResponseForReactor(exchange.getResponse());
        AuthStorage storage = new AuthStorageForReactor(exchange);
        AuthContextForThreadLocalStorage.setBox(request, response, storage);
    }

    /**
     * 获取上下文对象
     *
     * @return see note
     */
    public static ServerWebExchange getContext() {
        Box box = AuthContextForThreadLocalStorage.getBoxNotNull();
        return (ServerWebExchange) box.getStorage().getSource();
    }

    /**
     * 清除上下文对象
     */
    public static void clearContext() {
        AuthContextForThreadLocalStorage.clearBox();
    }

    /**
     * 写入上下文对象, 并在执行函数后将其清除
     *
     * @param exchange see note
     * @param fun      see note
     */
    public static void setContext(ServerWebExchange exchange, VoidFunction fun) {
        try {
            setContext(exchange);
            fun.run();
        } finally {
            clearContext();
        }
    }

}
