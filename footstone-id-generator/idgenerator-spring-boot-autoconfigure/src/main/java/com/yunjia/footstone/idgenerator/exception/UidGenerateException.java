/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.idgenerator.exception;

import com.yunjia.footstone.common.exception.BaseException;

/**
 * <p>
 * UidGenerateException
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/18 8:37 PM
 */
public class UidGenerateException extends BaseException {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -27048199131316992L;

    private static final int UID_GENERATOR_ERROR_CODE = 901;

    /**
     * Constructor with message & cause
     *
     * @param message
     * @param cause
     */
    public UidGenerateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with message
     *
     * @param message
     */
    public UidGenerateException(String message) {
        super(message);
    }

    /**
     * Constructor with message format
     *
     * @param msgFormat
     * @param args
     */
    public UidGenerateException(String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
    }

    /**
     * Constructor with cause
     *
     * @param cause
     */
    public UidGenerateException(Throwable cause) {
        super(cause);
    }

}
