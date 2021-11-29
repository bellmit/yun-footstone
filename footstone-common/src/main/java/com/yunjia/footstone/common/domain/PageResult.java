/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * 分页返回结构
 * </p>
 * @author sunkaiyun
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Schema
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 2559368536136491863L;

    @Schema(description = "当前页码", defaultValue = "1")
    private long currentPage = 1;

    @Schema(description = "页大小", defaultValue = "10")
    private long pageSize = 10;

    @Schema(description = "总页数", defaultValue = "0")
    private long pageTotal = 0;

    @Schema(description = "总记录数", defaultValue = "0")
    private long total = 0;

    @Schema(description = "当前页对象集合")
    private Collection<T> list;

    public PageResult(long currentPage, long pageSize, long total) {
        if(currentPage > 0) {
            this.currentPage = currentPage;
        }
        if(pageSize > 0) {
            this.pageSize = pageSize;
        }
        if(total > 0) {
            this.total = total;
        }
        this.pageTotal = total % pageSize > 0 ? (total / pageSize) + 1 : (total / pageSize);
    }

    public PageResult(long currentPage, long pageSize, long total, Collection<T> list) {
        if(currentPage > 0) {
            this.currentPage = currentPage;
        }
        if(pageSize > 0) {
            this.pageSize = pageSize;
        }
        if(total > 0) {
            this.total = total;
        }
        this.pageTotal = total % pageSize > 0 ? (total / pageSize) + 1 : (total / pageSize);
        this.list = list;
    }

    public static <T> PageResult<T> of(long currentPage, long pageSize) {
        PageResult<T> pageResult = new PageResult<>(currentPage, pageSize, 0);
        return pageResult;
    }

    public static <T> PageResult<T> of(long currentPage, long pageSize, long total) {
        PageResult<T> pageResult = new PageResult<>(currentPage, pageSize, total);
        return pageResult;
    }

    public static <T> PageResult<T> of(long currentPage, long pageSize, long total, Collection<T> list) {
        PageResult<T> pageResult = new PageResult<>(currentPage, pageSize, total, list);
        return pageResult;
    }
}
