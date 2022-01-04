/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.basic;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.yunjia.footstone.auth.core.context.AuthHolder;
import com.yunjia.footstone.auth.core.exception.NotBasicAuthException;
import com.yunjia.footstone.auth.core.AuthManager;

/**
 * <p>
 * Http Basic 认证模块
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 7:16 PM
 */
public class BasicTemplate {

    /**
     * 默认的 Realm 名称
     */
    public static final String DEFAULT_REALM = "Auth-Realm";

    /**
     * 设置响应头，并抛出异常
     *
     * @param realm 领域
     */
    public void throwNotBasicAuthException(String realm) {
        AuthHolder.getResponse().setStatus(401).setHeader("WWW-Authenticate", "Basic Realm=" + realm);
        throw new NotBasicAuthException();
    }

    /**
     * 获取浏览器提交的 Basic 参数 （裁剪掉前缀并解码）
     *
     * @return 值
     */
    public String getAuthorizationValue() {

        // 获取请求头 Authorization 参数
        String authorization = AuthHolder.getRequest().getHeader("Authorization");

        // 如果不是以 Basic 作为前缀，则视为无效
        if(authorization == null || !authorization.startsWith("Basic ")) {
            return null;
        }

        // 裁剪前缀并解码
        return Base64.decodeStr(authorization.substring(6));
    }

    /**
     * 对当前会话进行 Basic 校验（使用全局配置的账号密码），校验不通过则抛出异常
     */
    public void check() {
        check(DEFAULT_REALM, AuthManager.getConfig().getBasic());
    }

    /**
     * 对当前会话进行 Basic 校验（手动设置账号密码），校验不通过则抛出异常
     *
     * @param account 账号（格式为 user:password）
     */
    public void check(String account) {
        check(DEFAULT_REALM, account);
    }

    /**
     * 对当前会话进行 Basic 校验（手动设置 Realm 和 账号密码），校验不通过则抛出异常
     *
     * @param realm   领域
     * @param account 账号（格式为 user:password）
     */
    public void check(String realm, String account) {
        if(StrUtil.isBlank(account)) {
            account = AuthManager.getConfig().getBasic();
        }
        String authorization = getAuthorizationValue();
        if(StrUtil.isBlank(authorization) || !authorization.equals(account)) {
            throwNotBasicAuthException(realm);
        }
    }

}
