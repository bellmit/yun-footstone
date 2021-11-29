/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.mybatis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 数据库公共列
 * </p>
 * @author sunkaiyun
 */
@AllArgsConstructor
@Getter
public enum CommonDbColumn {

    LOGIC_CODE("logicCode", "logic_code"),
    IS_VALID("isValid", "is_valid"),
    IS_DELETE("isDelete", "is_delete"),
    CREATE_BY_CODE("createByCode", "create_by_code"),
    CREATE_BY_NAME("createByName", "create_by_name"),
    CREATE_TIME("createTime", "create_time"),
    UPDATE_BY_CODE("updateByCode", "update_by_code"),
    UPDATE_BY_NAME("updateByName", "update_by_name"),
    UPDATE_TIME("updateTime", "update_time");

    private String entityFieldName;

    private String dbFieldName;
}
