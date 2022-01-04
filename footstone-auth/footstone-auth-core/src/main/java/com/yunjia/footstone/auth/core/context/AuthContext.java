/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.context;

import com.yunjia.footstone.auth.core.context.model.AuthRequest;
import com.yunjia.footstone.auth.core.context.model.AuthResponse;
import com.yunjia.footstone.auth.core.context.model.AuthStorage;

/**
 * <p>
 * Auth 上下文处理器
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/24 6:37 PM
 */
public interface AuthContext {

    /**
     * 获取当前请求的 [Request] 对象
     *
     * @return see note
     */
    AuthRequest getRequest();

    /**
     * 获取当前请求的 [Response] 对象
     *
     * @return see note
     */
    AuthResponse getResponse();

    /**
     * 获取当前请求的 [存储器] 对象
     *
     * @return see note
     */
    AuthStorage getStorage();

    /**
     * 校验指定路由匹配符是否可以匹配成功指定路径
     *
     * @param pattern 路由匹配符
     * @param path    需要匹配的路径
     *
     * @return see note
     */
    boolean matchPath(String pattern, String path);

    /**
     * 此上下文是否有效
     *
     * @return /
     */
    default boolean isValid() {
        return false;
    }
}
