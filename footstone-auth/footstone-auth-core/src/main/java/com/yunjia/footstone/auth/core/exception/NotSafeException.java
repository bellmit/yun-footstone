/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.exception;

import com.yunjia.footstone.common.exception.BaseException;

/**
 * <p>
 * 代表会话未能通过二级认证
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 4:00 PM
 */
public class NotSafeException extends BaseException {

    private static final long serialVersionUID = -592300296644339032L;

    private static final int NOT_SAFE_ERROR_CODE = 4034;

    /** 异常提示语 */
    public static final String NOT_SAFE_MESSAGE = "二级认证失败";

    /**
     * 一个异常：代表会话未通过二级认证
     */
    public NotSafeException() {
        super(NOT_SAFE_ERROR_CODE, NOT_SAFE_MESSAGE);
    }
}
