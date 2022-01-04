/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.servlet.model;

import com.yunjia.footstone.auth.core.context.model.AuthStorage;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Storage for Servlet
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/29 7:12 PM
 */
public class AuthStorageForServlet implements AuthStorage {

    /**
     * 底层Request对象
     */
    protected HttpServletRequest request;

    /**
     * 实例化
     *
     * @param request request对象
     */
    public AuthStorageForServlet(HttpServletRequest request) {
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
     * 在 [Request作用域] 里写入一个值
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void set(String key, Object value) {
        request.setAttribute(key, value);
    }

    /**
     * 在 [Request作用域] 里获取一个值
     *
     * @param key 键
     *
     * @return 值
     */
    @Override
    public Object get(String key) {
        return request.getAttribute(key);
    }

    /**
     * 在 [Request作用域] 里删除一个值
     *
     * @param key 键
     */
    @Override
    public void delete(String key) {
        request.removeAttribute(key);
    }
}
