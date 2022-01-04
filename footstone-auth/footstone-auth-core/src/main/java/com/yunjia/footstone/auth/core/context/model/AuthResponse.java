/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.context.model;

/**
 * <p>
 * Response 包装类
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/24 6:45 PM
 */
public interface AuthResponse {

    /**
     * 获取底层源对象
     *
     * @return see note
     */
    Object getSource();

    /**
     * 删除指定Cookie
     *
     * @param name Cookie名称
     */
    default void deleteCookie(String name) {
        addCookie(name, null, null, null, 0);
    }

    /**
     * 写入指定Cookie
     *
     * @param name    Cookie名称
     * @param value   Cookie值
     * @param path    Cookie路径
     * @param domain  Cookie的作用域
     * @param timeout 过期时间 （秒）
     */
    default void addCookie(String name, String value, String path, String domain, int timeout) {
        AuthCookie authCookie = AuthCookie.builder().name(name).value(value).path(path).domain(domain).maxAge(timeout)
                .build();
        this.addCookie(authCookie);
    }

    /**
     * 写入指定Cookie
     *
     * @param cookie Cookie-Model
     */
    default void addCookie(AuthCookie cookie) {
        this.addHeader(AuthCookie.HEADER_NAME, cookie.toHeaderValue());
    }

    /**
     * 设置响应状态码
     *
     * @param sc 响应状态码
     *
     * @return 对象自身
     */
    AuthResponse setStatus(int sc);

    /**
     * 在响应头里写入一个值
     *
     * @param name  名字
     * @param value 值
     *
     * @return 对象自身
     */
    AuthResponse setHeader(String name, String value);

    /**
     * 在响应头里添加一个值
     *
     * @param name  名字
     * @param value 值
     *
     * @return 对象自身
     */
    AuthResponse addHeader(String name, String value);

    /**
     * 在响应头写入 [Server] 服务器名称
     *
     * @param value 服务器名称
     *
     * @return 对象自身
     */
    default AuthResponse setServer(String value) {
        return this.setHeader("Server", value);
    }

    /**
     * 重定向
     *
     * @param url 重定向地址
     *
     * @return 任意值
     */
    Object redirect(String url);
}
