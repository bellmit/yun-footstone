/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.router;

import com.yunjia.footstone.auth.core.exception.AuthException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Http 请求各种请求类型的枚举表示
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/29 3:20 PM
 */
public enum HttpMethod {

    GET,
    HEAD,
    POST,
    PUT,
    PATCH,
    DELETE,
    OPTIONS,
    TRACE,
    CONNECT,

    /**
     * 代表全部请求方式
     */
    ALL;

    private static final Map<String, HttpMethod> map = new HashMap<>();

    static {
        for(HttpMethod reqMethod : values()) {
            map.put(reqMethod.name(), reqMethod);
        }
    }

    /**
     * String 转 enum
     *
     * @param method 请求类型
     *
     * @return ReqMethod 对象
     */
    public static HttpMethod toEnum(String method) {
        if(method == null) {
            throw new AuthException("无效Method：" + method);
        }
        HttpMethod reqMethod = map.get(method.toUpperCase());
        if(reqMethod == null) {
            throw new AuthException("无效Method：" + method);
        }
        return reqMethod;
    }

    /**
     * String[] 转 enum[]
     *
     * @param methods 请求类型数组
     *
     * @return ReqMethod 对象
     */
    public static HttpMethod[] toEnumArray(String... methods) {
        HttpMethod[] arr = new HttpMethod[methods.length];
        for(int i = 0; i < methods.length; i++) {
            arr[i] = HttpMethod.toEnum(methods[i]);
        }
        return arr;
    }
}
