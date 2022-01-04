/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.config;

import cn.hutool.core.util.StrUtil;
import com.yunjia.footstone.auth.core.exception.AuthException;
import com.yunjia.footstone.common.domain.CommonResponse;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * SSO 单点登录模块 配置类 Model
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/23 7:55 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthSsoConfig {

    // ----------------- Server端相关配置

    /**
     * Ticket有效期 (单位: 秒)
     */
    public long ticketTimeout = 60 * 5;

    /**
     * 所有允许的授权回调地址，多个用逗号隔开 (不在此列表中的URL将禁止下放ticket)
     */
    public String allowUrl = "*";

    /**
     * 是否打开单点注销功能
     */
    public Boolean isSlo = true;

    /**
     * 是否打开模式三（此值为 true 时将使用 http 请求：校验ticket值、单点注销、获取userinfo）
     */
    public Boolean isHttp = false;

    /**
     * 接口调用秘钥 (用于SSO模式三单点注销的接口通信身份校验)
     */
    public String secretKey;


    // ----------------- Client端相关配置

    /**
     * 配置 Server 端单点登录授权地址
     */
    public String authUrl;

    /**
     * 配置 Server 端的 ticket 校验地址
     */
    public String checkTicketUrl;

    /**
     * 配置 Server 端查询 userinfo 地址
     */
    public String userinfoUrl;

    /**
     * 配置 Server 端单点注销地址
     */
    public String sloUrl;

    /**
     * 配置当前 Client 端的单点注销回调URL （为空时自动获取）
     */
    public String ssoLogoutCall;

    /**
     * 以数组形式写入允许的授权回调地址
     * @param url 所有集合
     * @return 对象自身
     */
    public AuthSsoConfig setAllow(String ...url) {
        this.allowUrl = StrUtil.join(StrUtil.COMMA, url);
        return this;
    }



    // -------------------- AuthSsoHandle 所有回调函数 --------------------


    /**
     * SSO-Server端：未登录时返回的View
     */
    public Supplier<Object> notLoginView = () -> "当前会话在SSO-Server认证中心尚未登录";

    /**
     * SSO-Server端：登录函数
     */
    public BiFunction<String, String, Object> doLoginHandle = (name, pwd) -> CommonResponse.responseFail();

    /**
     * SSO-Client端：自定义校验Ticket返回值的处理逻辑 （每次从认证中心获取校验Ticket的结果后调用）
     */
    public BiFunction<Object, String, Object> ticketResultHandle = null;

    /**
     * SSO-Client端：发送Http请求的处理函数
     */
    public Function<String, Object> sendHttp = url -> {throw new AuthException("请配置Http处理器");};


    /**
     * @param notLoginView SSO-Server端：未登录时返回的View
     * @return 对象自身
     */
    public AuthSsoConfig setNotLoginView(Supplier<Object> notLoginView) {
        this.notLoginView = notLoginView;
        return this;
    }

    /**
     * @param doLoginHandle SSO-Server端：登录函数
     * @return 对象自身
     */
    public AuthSsoConfig setDoLoginHandle(BiFunction<String, String, Object> doLoginHandle) {
        this.doLoginHandle = doLoginHandle;
        return this;
    }

    /**
     * @param ticketResultHandle SSO-Client端：自定义校验Ticket返回值的处理逻辑 （每次从认证中心获取校验Ticket的结果后调用）
     * @return 对象自身
     */
    public AuthSsoConfig setTicketResultHandle(BiFunction<Object, String, Object> ticketResultHandle) {
        this.ticketResultHandle = ticketResultHandle;
        return this;
    }

    /**
     * @param sendHttp SSO-Client端：发送Http请求的处理函数
     * @return 对象自身
     */
    public AuthSsoConfig setSendHttp(Function<String, Object> sendHttp) {
        this.sendHttp = sendHttp;
        return this;
    }

}
