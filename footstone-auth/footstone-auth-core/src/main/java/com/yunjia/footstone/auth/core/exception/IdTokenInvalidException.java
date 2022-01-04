/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.exception;

import com.yunjia.footstone.common.exception.BaseException;

/**
 * <p>
 * Token相关异常
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/25 12:02 PM
 */
public class IdTokenInvalidException extends BaseException {

    private static final int TOKEN_INVALID_ERROR_CODE = 401;

    /**
     * Constructor with message & cause
     */
    public IdTokenInvalidException(String message, Throwable cause) {
        super(TOKEN_INVALID_ERROR_CODE, message, cause);
    }

    /**
     * Constructor with message
     */
    public IdTokenInvalidException(String message) {
        super(TOKEN_INVALID_ERROR_CODE, message);
    }

    /**
     * Constructor with message format
     */
    public IdTokenInvalidException(String msgFormat, Object... args) {
        super(TOKEN_INVALID_ERROR_CODE, String.format(msgFormat, args));
    }

    /**
     * Constructor with cause
     */
    public IdTokenInvalidException(Throwable cause) {
        super(TOKEN_INVALID_ERROR_CODE, cause);
    }
}
