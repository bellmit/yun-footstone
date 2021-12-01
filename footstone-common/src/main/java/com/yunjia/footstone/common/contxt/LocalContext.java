/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.contxt;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import lombok.Data;

/**
 * <p>
 * 公共上下文
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 11:10 AM
 */
@Data
public class LocalContext extends BaseContext implements ContextInitializer, ContextExtracter, ContextInjector {

    /**
     * 登录用户
     */
    private LoginUser loginUser;

    /**
     * Header extract
     *
     * @return headers
     */
    @Override
    public Map<String, Collection<String>> extract() {
        final Map<String, Collection<String>> headers = Maps.newHashMap();
        return headers;
    }

    /**
     * Get http header or param inject context
     *
     * @param function left: key, right: result value
     *
     * @return context
     */
    @Override
    public LocalContext inject(Function<String, String> function) {
        setToken(function.apply(HeaderConstants.TOKEN));
        return this;
    }


    /**
     * 实例化系统上下文对象Context
     */
    @Override
    public LocalContext newInstance() {
        return new LocalContext();
    }
}
