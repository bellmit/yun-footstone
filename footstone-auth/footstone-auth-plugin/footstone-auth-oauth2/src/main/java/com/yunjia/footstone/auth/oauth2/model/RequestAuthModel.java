/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.oauth2.model;

import cn.hutool.core.util.StrUtil;
import com.yunjia.footstone.auth.core.exception.AuthException;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * 请求授权参数的Model
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/30 9:18 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RequestAuthModel implements Serializable {

    private static final long serialVersionUID = 2555000039318066121L;

    /**
     * 应用id
     */
    public String clientId;

    /**
     * 授权范围
     */
    public String scope;

    /**
     * 对应的账号id
     */
    public Object loginId;

    /**
     * 待重定向URL
     */
    public String redirectUri;

    /**
     * 授权类型, 非必填
     */
    public String responseType;

    /**
     * 状态标识, 可为null
     */
    public String state;

    /**
     * 检查此Model参数是否有效
     *
     * @return 对象自身
     */
    public RequestAuthModel checkModel() {
        if(StrUtil.isBlank(clientId)) {
            throw new AuthException("无效client_id");
        }
        if(StrUtil.isBlank(scope)) {
            throw new AuthException("无效scope");
        }
        if(StrUtil.isBlank(redirectUri)) {
            throw new AuthException("无效redirect_uri");
        }
        if(StrUtil.isBlank(String.valueOf(loginId))) {
            throw new AuthException("无效LoginId");
        }
        return this;
    }
}
