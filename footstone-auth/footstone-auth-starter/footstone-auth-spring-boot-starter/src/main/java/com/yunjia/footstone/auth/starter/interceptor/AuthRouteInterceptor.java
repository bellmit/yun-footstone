/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.starter.interceptor;

import com.yunjia.footstone.auth.core.exception.BackResultException;
import com.yunjia.footstone.auth.core.exception.StopMatchException;
import com.yunjia.footstone.auth.core.router.AuthRouteFunction;
import com.yunjia.footstone.auth.core.stp.StpUtil;
import com.yunjia.footstone.auth.servlet.model.AuthRequestForServlet;
import com.yunjia.footstone.auth.servlet.model.AuthResponseForServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * <p>
 * 基于路由的拦截式鉴权
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 1:53 PM
 */
public class AuthRouteInterceptor implements HandlerInterceptor {

    /**
     * 每次进入拦截器的[执行函数]，默认为登录校验
     */
    public AuthRouteFunction function = (req, res, handler) -> StpUtil.checkLogin();

    /**
     * 创建一个路由拦截器
     */
    public AuthRouteInterceptor() {
    }

    /**
     * 创建, 并指定[执行函数]
     *
     * @param function [执行函数]
     */
    public AuthRouteInterceptor(AuthRouteFunction function) {
        this.function = function;
    }

    /**
     * 静态方法快速构建一个
     *
     * @param function 自定义模式下的执行函数
     *
     * @return sa路由拦截器
     */
    public static AuthRouteInterceptor newInstance(AuthRouteFunction function) {
        return new AuthRouteInterceptor(function);
    }

    // ----------------- 验证方法 -----------------

    /**
     * 每次请求之前触发的方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        try {
            function.run(new AuthRequestForServlet(request), new AuthResponseForServlet(response), handler);
        } catch(StopMatchException e) {
            // 停止匹配，进入Controller
        } catch(BackResultException e) {
            // 停止匹配，向前端输出结果
            if(response.getContentType() == null) {
                response.setContentType("text/plain; charset=utf-8");
            }
            response.getWriter().print(e.getMessage());
            return false;
        }

        // 通过验证
        return true;
    }
}
