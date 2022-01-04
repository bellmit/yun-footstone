/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.token;

/**
 * <p>
 * 身份凭证模块-工具类
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 6:35 PM
 */
public class TokenUtil {

    /**
     * 在 Request 上储存 Id-Token 时建议使用的key
     */
    public static final String ID_TOKEN = TokenTemplate.ID_TOKEN;

    /**
     * 底层 TokenTemplate 对象
     */
    public static TokenTemplate tokenTemplate = new TokenTemplate();

    // -------------------- 获取 & 校验 

    /**
     * 获取当前Id-Token, 如果不存在，则立即创建并返回
     *
     * @return 当前token
     */
    public static String getToken() {
        return tokenTemplate.getToken();
    }

    /**
     * 判断一个Id-Token是否有效
     *
     * @param token 要验证的token
     *
     * @return 这个token是否有效
     */
    public static boolean isValid(String token) {
        return tokenTemplate.isValid(token);
    }

    /**
     * 校验一个Id-Token是否有效 (如果无效则抛出异常)
     *
     * @param token 要验证的token
     */
    public static void checkToken(String token) {
        tokenTemplate.checkToken(token);
    }

    /**
     * 校验当前Request提供的Id-Token是否有效 (如果无效则抛出异常)
     */
    public static void checkCurrentRequestToken() {
        tokenTemplate.checkCurrentRequestToken();
    }

    /**
     * 刷新一次Id-Token (注意集群环境中不要多个服务重复调用)
     *
     * @return 新Token
     */
    public static String refreshToken() {
        return tokenTemplate.refreshToken();
    }

    // -------------------- 获取Token

    /**
     * 获取Id-Token，不做任何处理
     *
     * @return token
     */
    public static String getTokenNh() {
        return tokenTemplate.getTokenNh();
    }

    /**
     * 获取Past-Id-Token，不做任何处理
     *
     * @return token
     */
    public static String getPastTokenNh() {
        return tokenTemplate.getPastTokenNh();
    }

}
