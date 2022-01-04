/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.session;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * Token 签名 Model
 * 挂在到SaSession上的token签名
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 10:09 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TokenSign implements Serializable {

    private static final long serialVersionUID = -8376535569644031533L;

    /**
     * token值
     */
    private String value;

    /**
     * 所在设备标识
     */
    private String device;

}
