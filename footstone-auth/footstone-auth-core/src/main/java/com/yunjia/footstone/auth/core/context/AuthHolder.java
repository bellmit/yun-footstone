/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.context;

import com.yunjia.footstone.auth.core.context.model.AuthResponse;
import com.yunjia.footstone.auth.core.context.model.AuthStorage;
import com.yunjia.footstone.auth.core.AuthManager;
import com.yunjia.footstone.auth.core.context.model.AuthRequest;

/**
 * <p>
 * 上下文持有类
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 9:48 AM
 */
public class AuthHolder {

    /**
     * 获取当前请求的 SaTokenContext
     *
     * @return see note
     */
    public static AuthContext getContext() {
        return AuthManager.getAuthContext();
    }

    /**
     * 获取当前请求的 [Request] 对象
     *
     * @return see note
     */
    public static AuthRequest getRequest() {
        return AuthManager.getAuthContext().getRequest();
    }

    /**
     * 获取当前请求的 [Response] 对象
     *
     * @return see note
     */
    public static AuthResponse getResponse() {
        return AuthManager.getAuthContext().getResponse();
    }

    /**
     * 获取当前请求的 [存储器] 对象
     *
     * @return see note
     */
    public static AuthStorage getStorage() {
        return AuthManager.getAuthContext().getStorage();
    }
}
