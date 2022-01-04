/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.starter.interceptor;

import com.yunjia.footstone.auth.core.strategy.AuthStrategy;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * <p>
 * 注解式鉴权 - 拦截器
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 1:52 PM
 */
public class AuthAnnotationInterceptor implements HandlerInterceptor {

    /**
     * 每次请求之前触发的方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // 获取处理method
        if(handler instanceof HandlerMethod == false) {
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();

        // 进行验证
        AuthStrategy.INSTANCE.checkMethodAnnotation.accept(method);

        // 通过验证
        return true;
    }
}
