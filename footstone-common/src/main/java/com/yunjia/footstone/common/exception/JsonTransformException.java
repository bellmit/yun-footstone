/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.exception;

/**
 * <p>
 * Json转换异常
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 15:34
 */
public class JsonTransformException extends BaseException {

    private static final long serialVersionUID = -3010456487227762210L;

    private static final int JSON_FORMAT_ERROR_CODE = 900;

    /**
     * 构造器
     *
     * @param message 异常详细信息
     * @param cause   异常原因
     */
    public JsonTransformException(String message, Throwable cause) {
        super(JSON_FORMAT_ERROR_CODE, message, cause);
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     */
    public JsonTransformException(String message) {
        super(JSON_FORMAT_ERROR_CODE, message);
    }

    /**
     * 构造器
     *
     * @param cause 异常原因
     */
    public JsonTransformException(Throwable cause) {
        super(JSON_FORMAT_ERROR_CODE, cause);
    }
}
