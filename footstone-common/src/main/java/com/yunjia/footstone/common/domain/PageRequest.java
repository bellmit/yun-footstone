/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 *     分页请求基类
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 10:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema
public class PageRequest extends OperatorRequest {

    @Schema(description = "当前页", defaultValue = "1")
    private long currentPage = 1;

    @Schema(description = "页大小", defaultValue = "10")
    private long pageSize = 10;

}
