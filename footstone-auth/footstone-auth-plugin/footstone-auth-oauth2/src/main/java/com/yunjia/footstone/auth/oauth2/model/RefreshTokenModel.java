/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.oauth2.model;

/**
 * <p>
 * Refresh-Token
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/30 9:17 AM
 */
public class RefreshTokenModel {

    /**
     * Refresh-Token 值
     */
    public String refreshToken;

    /**
     * Refresh-Token 到期时间
     */
    public long expiresTime;

    /**
     * 应用id
     */
    public String clientId;

    /**
     * 授权范围
     */
    public String scope;

    /**
     * 对应账号id
     */
    public Object loginId;

    /**
     * 对应账号id
     */
    public String openid;

    @Override
    public String toString() {
        return "RefreshTokenModel [refreshToken=" + refreshToken + ", expiresTime=" + expiresTime + ", clientId="
                + clientId + ", scope=" + scope + ", loginId=" + loginId + ", openid=" + openid + "]";
    }

    /**
     * 获取：此 Refresh-Token 的剩余有效期（秒）
     *
     * @return see note
     */
    public long getExpiresIn() {
        long s = (expiresTime - System.currentTimeMillis()) / 1000;
        return s < 1 ? -2 : s;
    }
}
