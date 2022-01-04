/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.starter.filter;

import com.yunjia.footstone.auth.core.constant.AuthConstant;
import com.yunjia.footstone.auth.core.exception.AuthException;
import com.yunjia.footstone.auth.core.exception.BackResultException;
import com.yunjia.footstone.auth.core.exception.StopMatchException;
import com.yunjia.footstone.auth.core.filter.AuthFilterErrorStrategy;
import com.yunjia.footstone.auth.core.filter.AuthFilterStrategy;
import com.yunjia.footstone.auth.core.router.AuthRouter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.core.annotation.Order;

/**
 * <p>
 * Servlet全局过滤器
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 11:25 AM
 */
@Order(AuthConstant.ASSEMBLY_ORDER)
public class AuthServletFilter implements Filter {

    // ------------------------ 设置此过滤器 拦截 & 放行 的路由 

    /**
     * 拦截路由
     */
    private List<String> includeList = new ArrayList<>();

    /**
     * 放行路由
     */
    private List<String> excludeList = new ArrayList<>();

    /**
     * 添加 [拦截路由]
     *
     * @param paths 路由
     *
     * @return 对象自身
     */
    public AuthServletFilter addInclude(String... paths) {
        includeList.addAll(Arrays.asList(paths));
        return this;
    }

    /**
     * 添加 [放行路由]
     *
     * @param paths 路由
     *
     * @return 对象自身
     */
    public AuthServletFilter addExclude(String... paths) {
        excludeList.addAll(Arrays.asList(paths));
        return this;
    }

    /**
     * 写入 [拦截路由] 集合
     *
     * @param pathList 路由集合
     *
     * @return 对象自身
     */
    public AuthServletFilter setIncludeList(List<String> pathList) {
        includeList = pathList;
        return this;
    }

    /**
     * 写入 [放行路由] 集合
     *
     * @param pathList 路由集合
     *
     * @return 对象自身
     */
    public AuthServletFilter setExcludeList(List<String> pathList) {
        excludeList = pathList;
        return this;
    }

    /**
     * 获取 [拦截路由] 集合
     *
     * @return see note
     */
    public List<String> getIncludeList() {
        return includeList;
    }

    /**
     * 获取 [放行路由] 集合
     *
     * @return see note
     */
    public List<String> getExcludeList() {
        return excludeList;
    }

    // ------------------------ 钩子函数

    /**
     * 认证函数：每次请求执行
     */
    public AuthFilterStrategy auth = r -> {
    };

    /**
     * 异常处理函数：每次[认证函数]发生异常时执行此函数
     */
    public AuthFilterErrorStrategy error = e -> {
        throw new AuthException(e);
    };

    /**
     * 前置函数：在每次[认证函数]之前执行
     */
    public AuthFilterStrategy beforeAuth = r -> {
    };

    /**
     * 写入[认证函数]: 每次请求执行
     *
     * @param auth see note
     *
     * @return 对象自身
     */
    public AuthServletFilter setAuth(AuthFilterStrategy auth) {
        this.auth = auth;
        return this;
    }

    /**
     * 写入[异常处理函数]：每次[认证函数]发生异常时执行此函数
     *
     * @param error see note
     *
     * @return 对象自身
     */
    public AuthServletFilter setError(AuthFilterErrorStrategy error) {
        this.error = error;
        return this;
    }

    /**
     * 写入[前置函数]：在每次[认证函数]之前执行
     *
     * @param beforeAuth see note
     *
     * @return 对象自身
     */
    public AuthServletFilter setBeforeAuth(AuthFilterStrategy beforeAuth) {
        this.beforeAuth = beforeAuth;
        return this;
    }

    // ------------------------ doFilter

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            // 执行全局过滤器 
            AuthRouter.match(includeList).notMatch(excludeList).check(r -> {
                beforeAuth.run(null);
                auth.run(null);
            });

        } catch(StopMatchException e) {

        } catch(Throwable e) {
            // 1. 获取异常处理策略结果 
            String result = (e instanceof BackResultException) ? e.getMessage() : String.valueOf(error.run(e));

            // 2. 写入输出流 
            if(response.getContentType() == null) {
                response.setContentType("text/plain; charset=utf-8");
            }
            response.getWriter().print(result);
            return;
        }

        // 执行 
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
