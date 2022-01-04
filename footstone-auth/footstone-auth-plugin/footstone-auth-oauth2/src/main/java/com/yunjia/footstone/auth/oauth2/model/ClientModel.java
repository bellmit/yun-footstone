/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.oauth2.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * Client应用信息
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/30 9:23 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ClientModel implements Serializable {

    private static final long serialVersionUID = 8615953574136928849L;

    /**
     * 应用id
     */
    public String clientId;

    /**
     * 应用秘钥
     */
    public String clientSecret;

    /**
     * 应用签约的所有权限, 多个用逗号隔开
     */
    public String contractScope;

    /**
     * 应用允许授权的所有URL, 多个用逗号隔开
     */
    public String allowUrl;

}
