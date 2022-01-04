/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.servlet.model;

import com.yunjia.footstone.auth.core.context.model.AuthResponse;
import com.yunjia.footstone.auth.core.exception.AuthException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * Response for Servlet
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/29 7:09 PM
 */
public class AuthResponseForServlet implements AuthResponse {

    /**
     * 底层Request对象
     */
    protected HttpServletResponse response;

    /**
     * 实例化
     *
     * @param response response对象
     */
    public AuthResponseForServlet(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * 获取底层源对象
     *
     * @return see note
     */
    @Override
    public Object getSource() {
        return response;
    }

    /**
     * 设置响应状态码
     *
     * @param sc 响应状态码
     *
     * @return 对象自身
     */
    @Override
    public AuthResponse setStatus(int sc) {
        response.setStatus(sc);
        return this;
    }

    /**
     * 在响应头里写入一个值
     *
     * @param name  名字
     * @param value 值
     *
     * @return 对象自身
     */
    @Override
    public AuthResponse setHeader(String name, String value) {
        response.setHeader(name, value);
        return this;
    }

    /**
     * 在响应头里添加一个值
     *
     * @param name  名字
     * @param value 值
     *
     * @return 对象自身
     */
    @Override
    public AuthResponse addHeader(String name, String value) {
        response.addHeader(name, value);
        return this;
    }

    /**
     * 重定向
     *
     * @param url 重定向地址
     *
     * @return 任意值
     */
    @Override
    public Object redirect(String url) {
        try {
            response.sendRedirect(url);
        } catch(IOException e) {
            throw new AuthException(e);
        }
        return null;
    }
}
