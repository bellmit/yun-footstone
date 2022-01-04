/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.context;

import com.yunjia.footstone.auth.core.context.model.AuthRequest;
import com.yunjia.footstone.auth.core.context.model.AuthResponse;
import com.yunjia.footstone.auth.core.context.model.AuthStorage;

/**
 * <p>
 * 上下文处理器 [ThreadLocal版本]
 * 使用 [ThreadLocal版本] 上下文处理器需要在全局过滤器或者拦截器内率先调用
 * AuthContextForThreadLocalStorage.setBox(req,res, sto) 初始化上下文
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 6:32 PM
 */
public class AuthContextForThreadLocal implements AuthContext {

    @Override
    public AuthRequest getRequest() {
        return AuthContextForThreadLocalStorage.getRequest();
    }

    @Override
    public AuthResponse getResponse() {
        return AuthContextForThreadLocalStorage.getResponse();
    }

    @Override
    public AuthStorage getStorage() {
        return AuthContextForThreadLocalStorage.getStorage();
    }

    @Override
    public boolean matchPath(String pattern, String path) {
        return false;
    }

    @Override
    public boolean isValid() {
        return AuthContextForThreadLocalStorage.getBox() != null;
    }

}
