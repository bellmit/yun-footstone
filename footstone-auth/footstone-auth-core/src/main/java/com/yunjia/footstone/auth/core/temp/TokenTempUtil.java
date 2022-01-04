/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.temp;

import com.yunjia.footstone.auth.core.AuthManager;

/**
 * <p>
 * 临时验证令牌模块
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 6:44 PM
 */
public class TokenTempUtil {

    /**
     * 根据value创建一个token
     *
     * @param value   指定值
     * @param timeout 有效期，单位：秒
     *
     * @return 生成的token
     */
    public static String createToken(Object value, long timeout) {
        return AuthManager.getTokenTemp().createToken(value, timeout);
    }

    /**
     * 解析token获取value
     *
     * @param token 指定token
     *
     * @return See Note
     */
    public static Object parseToken(String token) {
        return AuthManager.getTokenTemp().parseToken(token);
    }

    /**
     * 解析token获取value，并转换为指定类型
     *
     * @param token 指定token
     * @param cs    指定类型
     * @param <T>   默认值的类型
     *
     * @return See Note
     */
    public static <T> T parseToken(String token, Class<T> cs) {
        return AuthManager.getTokenTemp().parseToken(token, cs);
    }

    /**
     * 获取指定 token 的剩余有效期，单位：秒
     * <p> 返回值 -1 代表永久，-2 代表token无效
     *
     * @param token see note
     *
     * @return see note
     */
    public static long getTimeout(String token) {
        return AuthManager.getTokenTemp().getTimeout(token);
    }

    /**
     * 删除一个 token
     *
     * @param token 指定token
     */
    public static void deleteToken(String token) {
        AuthManager.getTokenTemp().deleteToken(token);
    }

}
