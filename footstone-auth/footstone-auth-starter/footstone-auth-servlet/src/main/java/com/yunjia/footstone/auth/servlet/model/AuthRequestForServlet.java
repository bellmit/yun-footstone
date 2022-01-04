/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.servlet.model;

import cn.hutool.core.util.StrUtil;
import com.yunjia.footstone.auth.core.AuthManager;
import com.yunjia.footstone.auth.core.context.model.AuthRequest;
import com.yunjia.footstone.auth.core.exception.AuthException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * Request for Servlet
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/29 7:00 PM
 */
public class AuthRequestForServlet implements AuthRequest {

    /**
     * 底层Request对象
     */
    protected HttpServletRequest request;

    /**
     * 实例化
     *
     * @param request request对象
     */
    public AuthRequestForServlet(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * 获取底层源对象
     *
     * @return see note
     */
    @Override
    public Object getSource() {
        return request;
    }

    /**
     * 在 [请求体] 里获取一个值
     *
     * @param name 键
     *
     * @return 值
     */
    @Override
    public String getParam(String name) {
        return request.getParameter(name);
    }

    /**
     * 在 [请求头] 里获取一个值
     *
     * @param name 键
     *
     * @return 值
     */
    @Override
    public String getHeader(String name) {
        return request.getHeader(name);
    }

    /**
     * 在 [Cookie作用域] 里获取一个值
     *
     * @param name 键
     *
     * @return 值
     */
    @Override
    public String getCookieValue(String name) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie != null && name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 返回当前请求path (不包括上下文名称)
     *
     * @return see note
     */
    @Override
    public String getRequestPath() {
        return request.getServletPath();
    }

    /**
     * 返回当前请求的url，不带query参数，例：http://xxx.com/test
     *
     * @return see note
     */
    @Override
    public String getUrl() {
        String currDomain = AuthManager.getConfig().getCurrDomain();
        if(StrUtil.isNotBlank(currDomain)) {
            return currDomain + this.getRequestPath();
        }
        return request.getRequestURL().toString();
    }

    /**
     * 返回当前请求的类型
     *
     * @return see note
     */
    @Override
    public String getMethod() {
        return request.getMethod();
    }

    /**
     * 转发请求
     */
    @Override
    public Object forward(String path) {
        try {
            HttpServletResponse response = (HttpServletResponse) AuthManager.getAuthContext().getResponse().getSource();
            request.getRequestDispatcher(path).forward(request, response);
            return null;
        } catch(ServletException | IOException e) {
            throw new AuthException(e);
        }
    }
}
