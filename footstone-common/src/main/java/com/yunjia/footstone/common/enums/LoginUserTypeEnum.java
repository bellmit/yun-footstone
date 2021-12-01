/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.enums;

import com.yunjia.footstone.common.util.Check;
import java.util.Arrays;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 登录用户类型
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 10:43 AM
 */
@AllArgsConstructor
@Getter
public enum LoginUserTypeEnum {

    CUSTOMER(1, AuthTypeEnum.JWT, "客户"),
    STAFF(5, AuthTypeEnum.JWT, "内部员工"),
    SUPPLIER(10, AuthTypeEnum.JWT, "第三方供应商"),
    WORKER(15, AuthTypeEnum.JWT, "服务者");

    /**
     * 编码
     */
    private Integer code;

    /**
     * 认证类型
     */
    private AuthTypeEnum authType;

    /**
     * 释义
     */
    private String describe;

    /**
     * 根据编码查询枚举
     */
    public static LoginUserTypeEnum getByCode(Integer code) {
        if(Check.isNull(code)) {
            return null;
        }
        Optional<LoginUserTypeEnum> optional = Arrays.stream(LoginUserTypeEnum.values())
                .filter(t -> t.getCode().equals(code)).findAny();
        return optional.isPresent() ? optional.get() : null;
    }

    /**
     * 获取登录认证方式
     */
    public static AuthTypeEnum getAuthTypeByCode(Integer code) {
        LoginUserTypeEnum loginUserTypeEnum = getByCode(code);
        return null == loginUserTypeEnum ? null : loginUserTypeEnum.getAuthType();
    }

    /**
     * 根据编码查询释义
     */
    public static String getDesByCode(Integer code) {
        LoginUserTypeEnum loginUserTypeEnum = getByCode(code);
        return null == loginUserTypeEnum ? "" : loginUserTypeEnum.getDescribe();
    }
}
