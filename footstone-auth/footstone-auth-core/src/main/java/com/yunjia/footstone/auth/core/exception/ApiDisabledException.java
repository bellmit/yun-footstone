/*
 * Copyright (c) 2022. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.exception;

import com.yunjia.footstone.common.exception.BaseException;

/**
 * <p>
 * 代表 API 已被禁用
 * </p>
 *
 * @author sunkaiyun
 * @date 2022/1/4 5:15 PM
 */
public class ApiDisabledException extends BaseException {

    private static final long serialVersionUID = 6379013106824078659L;

    private static final int API_DISABLED_ERROR_CODE = 4039;

    /**
     * 异常提示语
     */
    public static final String API_DISABLED_ERROR_MESSAGE = "This API is disabled";

    /**
     * 一个异常：代表 API 已被禁用
     */
    public ApiDisabledException() {
        super(API_DISABLED_ERROR_CODE, API_DISABLED_ERROR_MESSAGE);
    }

    /**
     * 一个异常：代表 API 已被禁用
     *
     * @param message 异常描述
     */
    public ApiDisabledException(String message) {
        super(API_DISABLED_ERROR_CODE, message);
    }
}
