/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.context;

import com.yunjia.footstone.auth.core.context.model.AuthRequest;
import com.yunjia.footstone.auth.core.context.model.AuthResponse;
import com.yunjia.footstone.auth.core.context.model.AuthStorage;
import com.yunjia.footstone.auth.core.exception.AuthException;

/**
 * <p>
 * 上下文处理器 [默认实现类]
 * 一般情况下框架会为你自动注入合适的上下文处理器，如果代码断点走到了此默认实现类，
 * 说明你引入的依赖有问题或者错误的调用了Auth的API
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 6:24 PM
 */
public class DefaultAuthContextImpl implements AuthContext {

    /**
     * 默认的上下文处理器对象
     */
    public static AuthContext defaultContext = new DefaultAuthContextImpl();

    /**
     * 默认的错误提示语
     */
    public static final String ERROR_MESSAGE = "未初始化任何有效上下文处理器";

    /**
     * 获取当前请求的 [Request] 对象
     */
    @Override
    public AuthRequest getRequest() {
        throw new AuthException(ERROR_MESSAGE);
    }

    /**
     * 获取当前请求的 [Response] 对象
     */
    @Override
    public AuthResponse getResponse() {
        throw new AuthException(ERROR_MESSAGE);
    }

    /**
     * 获取当前请求的 [存储器] 对象
     */
    @Override
    public AuthStorage getStorage() {
        throw new AuthException(ERROR_MESSAGE);
    }

    /**
     * 校验指定路由匹配符是否可以匹配成功指定路径
     */
    @Override
    public boolean matchPath(String pattern, String path) {
        throw new AuthException(ERROR_MESSAGE);
    }

}
