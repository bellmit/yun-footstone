/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.jwt;

import com.yunjia.footstone.auth.core.AuthManager;
import com.yunjia.footstone.auth.core.context.AuthHolder;
import com.yunjia.footstone.auth.core.exception.ApiDisabledException;
import com.yunjia.footstone.auth.core.exception.AuthException;
import com.yunjia.footstone.auth.core.exception.NotLoginException;
import com.yunjia.footstone.auth.core.repository.AuthRepository;
import com.yunjia.footstone.auth.core.stp.LoginModel;
import com.yunjia.footstone.auth.core.stp.StpLogic;
import com.yunjia.footstone.auth.core.stp.StpUtil;
import com.yunjia.footstone.auth.core.stp.TokenInfo;

/**
 * <p>
 * 整合 jwt -- stateless 无状态
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 5:11 PM
 */
public class StpLogicJwtForStateless extends StpLogic {


    /**
     * 整合 jwt -- stateless 无状态
     */
    public StpLogicJwtForStateless() {
        super(StpUtil.TYPE);
    }

    /**
     * 整合 jwt -- stateless 无状态
     *
     * @param loginType 账号体系标识
     */
    public StpLogicJwtForStateless(String loginType) {
        super(loginType);
    }

    /**
     * 获取jwt秘钥
     *
     * @return /
     */
    public String jwtSecretKey() {
        return getConfig().getJwtSecretKey();
    }

    //
    // ------ 重写方法
    //

    // ------------------- 获取token 相关 -------------------

    /**
     * 创建一个TokenValue
     */
    @Override
    public String createTokenValue(Object loginId, String device, long timeout) {
        return AuthJwtUtil.createToken(loginType, loginId, device, timeout, jwtSecretKey());
    }

    /**
     * 获取当前会话的Token信息
     *
     * @return token信息
     */
    @Override
    public TokenInfo getTokenInfo() {
        TokenInfo info = new TokenInfo();
        info.tokenName = getTokenName();
        info.tokenValue = getTokenValue();
        info.isLogin = isLogin();
        info.loginId = getLoginIdDefaultNull();
        info.loginType = getLoginType();
        info.tokenTimeout = getTokenTimeout();
        info.sessionTimeout = AuthRepository.NOT_VALUE_EXPIRE;
        info.tokenSessionTimeout = AuthRepository.NOT_VALUE_EXPIRE;
        info.tokenActivityTimeout = AuthRepository.NOT_VALUE_EXPIRE;
        info.loginDevice = getLoginDevice();
        return info;
    }

    // ------------------- 登录相关操作 -------------------

    /**
     * 会话登录，并指定所有登录参数Model
     */
    @Override
    public void login(Object id, LoginModel loginModel) {

        AuthException.throwByNull(id, "账号id不能为空");

        // ------ 1、初始化 loginModel
        loginModel.build(getConfig());

        // ------ 2、生成一个token
        String tokenValue = createTokenValue(id, loginModel.getDeviceOrDefault(), loginModel.getTimeout());

        // 3、在当前会话写入tokenValue
        setTokenValue(tokenValue, loginModel.getCookieTimeout());

        // $$ 通知监听器，账号xxx 登录成功
        AuthManager.getAuthListener().doLogin(loginType, id, loginModel);
    }

    /**
     * 获取指定Token对应的账号id (不做任何特殊处理)
     */
    @Override
    public String getLoginIdNotHandle(String tokenValue) {
        // 先验证 loginType，如果不符，则视为无效token，返回null
        String loginType = AuthJwtUtil.getPayloadsNotCheck(tokenValue, jwtSecretKey()).getStr(AuthJwtUtil.LOGIN_TYPE);
        if(getLoginType().equals(loginType) == false) {
            return null;
        }
        // 获取 loginId
        try {
            Object loginId = AuthJwtUtil.getLoginId(tokenValue, jwtSecretKey());
            return String.valueOf(loginId);
        } catch(NotLoginException e) {
            return null;
        }
    }

    /**
     * 会话注销
     */
    @Override
    public void logout() {
        // ...

        // 从当前 [storage存储器] 里删除
        AuthHolder.getStorage().delete(splicingKeyJustCreatedSave());

        // 如果打开了Cookie模式，则把cookie清除掉
        if(getConfig().getIsReadCookie()) {
            AuthHolder.getResponse().deleteCookie(getTokenName());
        }
    }

    // ------------------- 过期时间相关 -------------------

    /**
     * 获取当前登录者的 token 剩余有效时间 (单位: 秒)
     */
    @Override
    public long getTokenTimeout() {
        return AuthJwtUtil.getTimeout(getTokenValue(), jwtSecretKey());
    }

    // ------------------- id 反查 token 相关操作 -------------------

    /**
     * 返回当前会话的登录设备
     *
     * @return 当前令牌的登录设备
     */
    @Override
    public String getLoginDevice() {
        // 如果没有token，直接返回 null
        String tokenValue = getTokenValue();
        if(tokenValue == null) {
            return null;
        }
        // 如果还未登录，直接返回 null
        if(!isLogin()) {
            return null;
        }
        // 获取
        return AuthJwtUtil.getPayloadsNotCheck(tokenValue, jwtSecretKey()).getStr(AuthJwtUtil.DEVICE);
    }

    // ------------------- Bean对象代理 -------------------

    /**
     * [禁用] 返回持久化对象
     */
    @Override
    public AuthRepository getAuthRepository() {
        throw new ApiDisabledException();
    }

}
