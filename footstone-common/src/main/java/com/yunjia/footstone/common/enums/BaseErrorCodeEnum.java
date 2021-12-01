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
 * 基础异常码定义
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 10:57
 */
@AllArgsConstructor
@Getter
public enum BaseErrorCodeEnum {

    OK(200, "操作成功"),
    FAIL(999, "操作失败"),
    UNDEFINED(-1, "未定义");

    /**
     * 编码
     */
    private Integer code;

    /**
     * 释义
     */
    private String describe;

    /**
     * 根据编码查询枚举
     */
    public static BaseErrorCodeEnum getByCode(Integer code) {
        if(Check.isNull(code)) {
            return null;
        }
        Optional<BaseErrorCodeEnum> optional = Arrays.stream(BaseErrorCodeEnum.values())
                .filter(t -> t.getCode().equals(code)).findAny();
        return optional.isPresent() ? optional.get() : null;
    }

    /**
     * 根据编码查询释义
     */
    public static String getDesByCode(Integer code) {
        BaseErrorCodeEnum baseErrorCodeEnum = getByCode(code);
        return null == baseErrorCodeEnum ? "" : baseErrorCodeEnum.getDescribe();
    }
}
