/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.oauth2.config;

import com.yunjia.footstone.common.domain.CommonResponse;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * OAuth2 配置类 Model
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/29 7:21 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OAuth2Config {

    /**
     * 是否打开模式：授权码（Authorization Code）
     */
    public Boolean isCode = true;

    /**
     * 是否打开模式：隐藏式（Implicit）
     */
    public Boolean isImplicit = false;

    /**
     * 是否打开模式：密码式（Password）
     */
    public Boolean isPassword = false;

    /**
     * 是否打开模式：凭证式（Client Credentials）
     */
    public Boolean isClient = false;

    /**
     * 是否在每次 Refresh-Token 刷新 Access-Token 时，产生一个新的 Refresh-Token
     */
    public Boolean isNewRefresh = false;

    /**
     * Code授权码 保存的时间(单位秒) 默认五分钟
     */
    public long codeTimeout = 60 * 5;

    /**
     * Access-Token 保存的时间(单位秒) 默认两个小时
     */
    public long accessTokenTimeout = 60 * 60 * 2;

    /**
     * Refresh-Token 保存的时间(单位秒) 默认30 天
     */
    public long refreshTokenTimeout = 60 * 60 * 24 * 30;

    /**
     * Client-Token 保存的时间(单位秒) 默认两个小时
     */
    public long clientTokenTimeout = 60 * 60 * 2;

    // -------------------- SaOAuth2Handle 所有回调函数 --------------------

    /**
     * OAuth-Server端：未登录时返回的View
     */
    public Supplier<Object> notLoginView = () -> "当前会话在OAuth-Server认证中心尚未登录";

    /**
     * OAuth-Server端：确认授权时返回的View
     */
    public BiFunction<String, String, Object> confirmView = (clientId, scope) -> "本次操作需要用户授权";

    /**
     * OAuth-Server端：登录函数
     */
    public BiFunction<String, String, Object> doLoginHandle = (name, pwd) -> CommonResponse.responseFail();

}
