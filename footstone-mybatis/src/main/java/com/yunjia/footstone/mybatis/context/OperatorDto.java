/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.mybatis.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * 操作人实体类
 * </p>
 * @author sunkaiyun
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OperatorDto {

    /**
     * 操作人ID
     */
    private String operatorId;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 操作人类型
     */
    private String operatorType;
}
