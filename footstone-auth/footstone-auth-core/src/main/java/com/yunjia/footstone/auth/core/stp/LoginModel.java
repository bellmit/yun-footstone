/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.stp;

import com.yunjia.footstone.auth.core.AuthManager;
import com.yunjia.footstone.auth.core.config.AuthConfig;
import com.yunjia.footstone.auth.core.constant.AuthConstant;
import com.yunjia.footstone.auth.core.repository.AuthRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * 调用 `StpUtil.login()` 时的 [配置参数 Model ]
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 10:57 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LoginModel {

    /**
     * 此次登录的客户端设备标识
     */
    public String device;

    /**
     * 是否为持久Cookie（临时Cookie在浏览器关闭时会自动删除，持久Cookie在重新打开后依然存在）
     */
    public Boolean isLastingCookie;

    /**
     * 指定此次登录token的有效期, 单位:秒 （如未指定，自动取全局配置的timeout值）
     */
    public Long timeout;

    /**
     * @return Cookie时长
     */
    public int getCookieTimeout() {
        if(isLastingCookie == false) {
            return -1;
        }
        if(timeout == AuthRepository.NEVER_EXPIRE) {
            return Integer.MAX_VALUE;
        }
        return (int)(long)timeout;
    }

    /**
     * @return 获取device参数，如果为null，则返回默认值
     */
    public String getDeviceOrDefault() {
        if(device == null) {
            return AuthConstant.DEFAULT_LOGIN_DEVICE;
        }
        return device;
    }

    /**
     * 构建对象，初始化默认值
     * @return 对象自身
     */
    public LoginModel build() {
        return build(AuthManager.getConfig());
    }

    /**
     * 构建对象，初始化默认值
     * @param config 配置对象
     * @return 对象自身
     */
    public LoginModel build(AuthConfig config) {
        if(isLastingCookie == null) {
            isLastingCookie = true;
        }
        if(timeout == null) {
            timeout = config.getTimeout();
        }
        return this;
    }

    /**
     * 静态方法获取一个 LoginModel 对象
     * @return LoginModel 对象
     */
    public static LoginModel create() {
        return new LoginModel();
    }

}
