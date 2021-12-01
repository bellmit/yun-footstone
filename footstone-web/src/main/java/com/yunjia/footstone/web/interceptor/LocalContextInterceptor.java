/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.web.interceptor;

import cn.hutool.core.util.StrUtil;
import com.yunjia.footstone.common.contxt.LocalContext;
import com.yunjia.footstone.common.contxt.LocalContextHolder;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

/**
 * <p>
 * LocalContext Interceptor
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 2:12 PM
 */
public class LocalContextInterceptor<T extends LocalContext> implements AsyncHandlerInterceptor {

    private final Class<T> contextClass;

    public LocalContextInterceptor() {
        this((Class<T>) LocalContext.class);
    }

    public LocalContextInterceptor(Class<T> contextClass) {
        this.contextClass = contextClass;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        LocalContextHolder.set(contextClass.newInstance().inject(getHeaderOrParamFunction(request)));
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        LocalContextHolder.clear();
    }

    public Function<String, String> getHeaderOrParamFunction(HttpServletRequest request) {
        return key -> StrUtil.isBlank(request.getHeader(key)) ? StrUtil.nullToEmpty(request.getParameter(key))
                : StrUtil.nullToEmpty(request.getHeader(key));
    }
}
