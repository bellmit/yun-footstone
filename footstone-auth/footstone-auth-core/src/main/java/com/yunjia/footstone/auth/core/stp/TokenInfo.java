/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.stp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * Token信息Model: 用来描述一个Token的常用参数
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 2:21 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TokenInfo {

    /**
     * token名称
     */
    public String tokenName;

    /**
     * token值
     */
    public String tokenValue;

    /**
     * 此token是否已经登录
     */
    public Boolean isLogin;

    /**
     * 此token对应的LoginId，未登录时为null
     */
    public Object loginId;

    /**
     * 账号类型
     */
    public String loginType;

    /**
     * token剩余有效期 (单位: 秒)
     */
    public long tokenTimeout;

    /**
     * User-Session剩余有效时间 (单位: 秒)
     */
    public long sessionTimeout;

    /**
     * Token-Session剩余有效时间 (单位: 秒)
     */
    public long tokenSessionTimeout;

    /**
     * token剩余无操作有效时间 (单位: 秒)
     */
    public long tokenActivityTimeout;

    /**
     * 登录设备标识
     */
    public String loginDevice;

    /**
     * 自定义数据
     */
    public String tag;
}
