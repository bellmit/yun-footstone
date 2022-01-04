/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.jwt;

import com.yunjia.footstone.auth.core.context.AuthHolder;
import com.yunjia.footstone.auth.core.exception.ApiDisabledException;
import com.yunjia.footstone.auth.core.exception.NotLoginException;
import com.yunjia.footstone.auth.core.repository.AuthRepository;
import com.yunjia.footstone.auth.core.stp.StpLogic;
import com.yunjia.footstone.auth.core.stp.StpUtil;
import com.yunjia.footstone.auth.core.stp.TokenInfo;
import java.util.List;

/**
 * <p>
 * 整合 jwt -- Mix 混入
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 5:18 PM
 */
public class StpLogicJwtForMix extends StpLogic {


    /**
     * 整合 jwt -- Mix 混入
     */
    public StpLogicJwtForMix() {
        super(StpUtil.TYPE);
    }

    /**
     * 整合 jwt -- Mix 混入
     *
     * @param loginType 账号体系标识
     */
    public StpLogicJwtForMix(String loginType) {
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

    /**
     * [禁用] 会话注销，根据账号id 和 设备标识
     */
    @Override
    public void logout(Object loginId, String device) {
        throw new ApiDisabledException();
    }

    /**
     * [禁用] 会话注销，根据指定 Token
     */
    @Override
    public void logoutByTokenValue(String tokenValue) {
        throw new ApiDisabledException();
    }

    /**
     * [禁用] 踢人下线，根据账号id 和 设备标识
     */
    @Override
    public void kickOut(Object loginId, String device) {
        throw new ApiDisabledException();
    }

    /**
     * [禁用] 踢人下线，根据指定 Token
     */
    @Override
    public void kickOutByTokenValue(String tokenValue) {
        throw new ApiDisabledException();
    }

    /**
     * [禁用] 顶人下线，根据账号id 和 设备标识
     */
    @Override
    public void replaced(Object loginId, String device) {
        throw new ApiDisabledException();
    }

    /**
     * 删除 Token-Id 映射
     */
    @Override
    public void deleteTokenToIdMapping(String tokenValue) {
        // not action
    }

    /**
     * 更改 Token 指向的 账号Id 值
     */
    @Override
    public void updateTokenToIdMapping(String tokenValue, Object loginId) {
        // not action
    }

    /**
     * 存储 Token-Id 映射
     */
    @Override
    public void saveTokenToIdMapping(String tokenValue, Object loginId, long timeout) {
        // not action
    }

    // ------------------- 过期时间相关 -------------------

    /**
     * 获取当前登录者的 token 剩余有效时间 (单位: 秒)
     */
    @Override
    public long getTokenTimeout() {
        return AuthJwtUtil.getTimeout(getTokenValue(), jwtSecretKey());
    }

    // ------------------- 会话管理 -------------------

    /**
     * [禁用] 根据条件查询Token
     */
    @Override
    public List<String> searchTokenValue(String keyword, int start, int size) {
        throw new ApiDisabledException();
    }

    // ------------------- Bean对象代理 -------------------

    /**
     * 返回全局配置对象的isShare属性
     *
     * @return /
     */
    @Override
    public boolean getConfigOfIsShare() {
        return false;
    }

}
