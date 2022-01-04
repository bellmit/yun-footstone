/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.jwt;

import com.yunjia.footstone.auth.core.stp.StpLogic;
import com.yunjia.footstone.auth.core.stp.StpUtil;

/**
 * <p>
 * 整合 jwt -- Style模式
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 5:03 PM
 */
public class StpLogicJwtForStyle extends StpLogic {

    /**
     * 整合 jwt -- Style模式
     */
    public StpLogicJwtForStyle() {
        super(StpUtil.TYPE);
    }

    /**
     * 整合 jwt -- Style模式
     * @param loginType 账号体系标识
     */
    public StpLogicJwtForStyle(String loginType) {
        super(loginType);
    }

    /**
     * 获取jwt秘钥
     * @return /
     */
    public String jwtSecretKey() {
        return getConfig().getJwtSecretKey();
    }

    // ------ 重写方法

    /**
     * 创建一个TokenValue
     * @param loginId loginId
     * @return 生成的tokenValue
     */
    @Override
    public String createTokenValue(Object loginId, String device, long timeout) {
        return AuthJwtUtil.createToken(loginId, jwtSecretKey());
    }
}
