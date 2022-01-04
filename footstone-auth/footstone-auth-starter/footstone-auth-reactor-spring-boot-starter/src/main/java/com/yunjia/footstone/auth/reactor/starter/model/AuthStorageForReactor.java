/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.reactor.starter.model;

import com.yunjia.footstone.auth.core.context.model.AuthStorage;
import org.springframework.web.server.ServerWebExchange;

/**
 * <p>
 * Storage for Reactor
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 4:18 PM
 */
public class AuthStorageForReactor implements AuthStorage {

    /**
     * 底层Request对象
     */
    protected ServerWebExchange exchange;

    /**
     * 实例化
     *
     * @param exchange exchange对象
     */
    public AuthStorageForReactor(ServerWebExchange exchange) {
        this.exchange = exchange;
    }

    /**
     * 获取底层源对象
     */
    @Override
    public Object getSource() {
        return exchange;
    }

    /**
     * 在 [Request作用域] 里写入一个值
     */
    @Override
    public void set(String key, Object value) {
        exchange.getAttributes().put(key, value);
    }

    /**
     * 在 [Request作用域] 里获取一个值
     */
    @Override
    public Object get(String key) {
        return exchange.getAttributes().get(key);
    }

    /**
     * 在 [Request作用域] 里删除一个值
     */
    @Override
    public void delete(String key) {
        exchange.getAttributes().remove(key);
    }

}
