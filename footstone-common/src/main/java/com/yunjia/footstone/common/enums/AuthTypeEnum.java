/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 登录认证方式
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 10:50 AM
 */
@AllArgsConstructor
@Getter
public enum AuthTypeEnum {

    JWT,
    PASSWORD;
}
