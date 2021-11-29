/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.mybatis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * YesOrNo枚举
 * </p>
 * @author sunkaiyun
 */
@AllArgsConstructor
@Getter
public enum YesOrNo {

    NO(0),
    YES(1);

    private Integer code;
}
