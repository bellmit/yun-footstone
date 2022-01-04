/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.reactor.starter.filter;

import com.yunjia.footstone.auth.core.constant.AuthConstant;
import com.yunjia.footstone.auth.core.exception.AuthException;
import com.yunjia.footstone.auth.core.exception.BackResultException;
import com.yunjia.footstone.auth.core.exception.StopMatchException;
import com.yunjia.footstone.auth.core.filter.AuthFilterErrorStrategy;
import com.yunjia.footstone.auth.core.filter.AuthFilterStrategy;
import com.yunjia.footstone.auth.core.router.AuthRouter;
import com.yunjia.footstone.auth.reactor.starter.context.AuthReactorHolder;
import com.yunjia.footstone.auth.reactor.starter.context.AuthReactorSyncHolder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * <p>
 * Reactor全局过滤器
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 4:06 PM
 */
@Order(AuthConstant.ASSEMBLY_ORDER)
public class AuthReactorFilter implements WebFilter {

    // ------------------------ 设置此过滤器 拦截 & 放行 的路由

    /**
     * 拦截路由
     */
    private List<String> includeList = new ArrayList<>();

    /**
     * 放行路由
     */
    private List<String> excludeList = new ArrayList<>();

    /**
     * 添加 [拦截路由]
     *
     * @param paths 路由
     *
     * @return 对象自身
     */
    public AuthReactorFilter addInclude(String... paths) {
        includeList.addAll(Arrays.asList(paths));
        return this;
    }

    /**
     * 添加 [放行路由]
     *
     * @param paths 路由
     *
     * @return 对象自身
     */
    public AuthReactorFilter addExclude(String... paths) {
        excludeList.addAll(Arrays.asList(paths));
        return this;
    }

    /**
     * 写入 [拦截路由] 集合
     *
     * @param pathList 路由集合
     *
     * @return 对象自身
     */
    public AuthReactorFilter setIncludeList(List<String> pathList) {
        includeList = pathList;
        return this;
    }

    /**
     * 写入 [放行路由] 集合
     *
     * @param pathList 路由集合
     *
     * @return 对象自身
     */
    public AuthReactorFilter setExcludeList(List<String> pathList) {
        excludeList = pathList;
        return this;
    }

    /**
     * 获取 [拦截路由] 集合
     *
     * @return see note
     */
    public List<String> getIncludeList() {
        return includeList;
    }

    /**
     * 获取 [放行路由] 集合
     *
     * @return see note
     */
    public List<String> getExcludeList() {
        return excludeList;
    }

    // ------------------------ 钩子函数

    /**
     * 认证函数：每次请求执行
     */
    public AuthFilterStrategy auth = r -> {
    };

    /**
     * 异常处理函数：每次[认证函数]发生异常时执行此函数
     */
    public AuthFilterErrorStrategy error = e -> {
        throw new AuthException(e);
    };

    /**
     * 前置函数：在每次[认证函数]之前执行
     */
    public AuthFilterStrategy beforeAuth = r -> {
    };

    /**
     * 写入[认证函数]: 每次请求执行
     *
     * @param auth see note
     *
     * @return 对象自身
     */
    public AuthReactorFilter setAuth(AuthFilterStrategy auth) {
        this.auth = auth;
        return this;
    }

    /**
     * 写入[异常处理函数]：每次[认证函数]发生异常时执行此函数
     *
     * @param error see note
     *
     * @return 对象自身
     */
    public AuthReactorFilter setError(AuthFilterErrorStrategy error) {
        this.error = error;
        return this;
    }

    /**
     * 写入[前置函数]：在每次[认证函数]之前执行
     *
     * @param beforeAuth see note
     *
     * @return 对象自身
     */
    public AuthReactorFilter setBeforeAuth(AuthFilterStrategy beforeAuth) {
        this.beforeAuth = beforeAuth;
        return this;
    }

    // ------------------------ filter

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        // 写入WebFilterChain对象
        exchange.getAttributes().put(AuthReactorHolder.CHAIN_KEY, chain);

        // ---------- 全局认证处理
        try {
            // 写入全局上下文 (同步)
            AuthReactorSyncHolder.setContext(exchange);

            // 执行全局过滤器
            AuthRouter.match(includeList).notMatch(excludeList).check(r -> {
                beforeAuth.run(null);
                auth.run(null);
            });

        } catch(StopMatchException e) {

        } catch(Throwable e) {
            // 1. 获取异常处理策略结果
            String result = (e instanceof BackResultException) ? e.getMessage() : String.valueOf(error.run(e));

            // 2. 写入输出流
            if(exchange.getResponse().getHeaders().getFirst("Content-Type") == null) {
                exchange.getResponse().getHeaders().set("Content-Type", "text/plain; charset=utf-8");
            }
            return exchange.getResponse()
                    .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(result.getBytes())));

        } finally {
            // 清除上下文
            AuthReactorSyncHolder.clearContext();
        }

        // ---------- 执行

        // 写入全局上下文 (同步)
        AuthReactorSyncHolder.setContext(exchange);

        // 执行
        return chain.filter(exchange).contextWrite(ctx -> {
            // 写入全局上下文 (异步)
            ctx = ctx.put(AuthReactorHolder.CONTEXT_KEY, exchange);
            return ctx;
        }).doFinally(r -> {
            // 清除上下文
            AuthReactorSyncHolder.clearContext();
        });
    }

}
