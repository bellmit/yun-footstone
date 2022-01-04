/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.temp;

import cn.hutool.core.convert.Convert;
import com.yunjia.footstone.auth.core.AuthManager;
import com.yunjia.footstone.auth.core.strategy.AuthStrategy;

/**
 * <p>
 * 临时令牌验证模块接口
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 6:39 PM
 */
public interface TokenTempInterface {

    /**
     * 根据value创建一个token
     *
     * @param value   指定值
     * @param timeout 有效期，单位：秒
     *
     * @return 生成的token
     */
    default String createToken(Object value, long timeout) {

        // 生成 token
        String token = AuthStrategy.INSTANCE.createToken.apply(null, null);

        // 持久化映射关系
        String key = splicingKeyTempToken(token);
        AuthManager.getAuthRepository().setObject(key, value, timeout);

        // 返回
        return token;
    }

    /**
     * 解析token获取value
     *
     * @param token 指定token
     *
     * @return See Note
     */
    default Object parseToken(String token) {
        String key = splicingKeyTempToken(token);
        return AuthManager.getAuthRepository().getObject(key);
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
    default <T> T parseToken(String token, Class<T> cs) {
        return Convert.convert(cs, parseToken(token));
    }

    /**
     * 获取指定 token 的剩余有效期，单位：秒
     * <p> 返回值 -1 代表永久，-2 代表token无效
     *
     * @param token see note
     *
     * @return see note
     */
    default long getTimeout(String token) {
        String key = splicingKeyTempToken(token);
        return AuthManager.getAuthRepository().getObjectTimeout(key);
    }

    /**
     * 删除一个 token
     *
     * @param token 指定token
     */
    default void deleteToken(String token) {
        String key = splicingKeyTempToken(token);
        AuthManager.getAuthRepository().deleteObject(key);
    }

    /**
     * 获取映射关系的持久化key
     *
     * @param token token值
     *
     * @return key
     */
    default String splicingKeyTempToken(String token) {
        return AuthManager.getConfig().getTokenName() + ":temp-token:" + token;
    }

    /**
     * @return jwt秘钥 (只有集成 sa-token-temp-jwt 模块时此参数才会生效)
     */
    default String getJwtSecretKey() {
        return null;
    }

}
