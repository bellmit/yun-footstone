/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.router;

import com.yunjia.footstone.auth.core.context.model.AuthResponse;
import com.yunjia.footstone.auth.core.context.model.AuthRequest;

/**
 * <p>
 * 路由拦截器验证方法Lambda
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/29 3:18 PM
 */
public interface AuthRouteFunction {

    /**
     * 执行验证的方法
     *
     * @param request  Request包装对象
     * @param response Response包装对象
     * @param handler  处理对象
     */
    void run(AuthRequest request, AuthResponse response, Object handler);
}
