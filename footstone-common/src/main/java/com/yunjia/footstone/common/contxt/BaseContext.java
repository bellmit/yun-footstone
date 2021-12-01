/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.contxt;

import com.yunjia.footstone.common.enums.BizSourceEnum;
import com.yunjia.footstone.common.enums.LoginUserTypeEnum;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * 基础上下文
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 10:39 AM
 */
@Data
public abstract class BaseContext implements ContextClear, Serializable {

    private static final long serialVersionUID = 3995510093179401032L;

    /**
     * 登录用户token
     */
    private String token;

    /**
     * 请求时间戳
     */
    private Long timestamp;

    /**
     * 请求来源
     */
    private BizSourceEnum bizSource;

    /**
     * 登录用户类型
     */
    private LoginUserTypeEnum userType;

}
