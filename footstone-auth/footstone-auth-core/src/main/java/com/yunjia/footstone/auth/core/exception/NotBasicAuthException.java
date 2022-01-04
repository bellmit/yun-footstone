/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.exception;

import com.yunjia.footstone.common.exception.BaseException;

/**
 * <p>
 * 代表会话未能通过 Http Basic 认证
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 7:17 PM
 */
public class NotBasicAuthException extends BaseException {

    private static final long serialVersionUID = 6550247056652689631L;

    private static final int NOT_BASIC_AUTH_ERROR_CODE = 4035;

    /**
     * 异常提示语
     */
    public static final String NOT_BASIC_AUTH_MESSAGE = "no basic auth";

    /**
     * 一个异常：代表会话未通过 Http Basic 认证
     */
    public NotBasicAuthException() {
        super(NOT_BASIC_AUTH_ERROR_CODE, NOT_BASIC_AUTH_MESSAGE);
    }
}
