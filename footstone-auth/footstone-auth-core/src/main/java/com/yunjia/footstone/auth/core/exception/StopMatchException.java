/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.exception;

import com.yunjia.footstone.common.exception.BaseException;

/**
 * <p>
 * 代表停止路由匹配，进入Controller
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/29 3:36 PM
 */
public class StopMatchException extends BaseException {

    private static final long serialVersionUID = 3120215157551822303L;

    private static final int STOP_MATCH_ERROR_CODE = 4036;

    /**
     * 构造
     */
    public StopMatchException() {
        super(STOP_MATCH_ERROR_CODE, "stop match");
    }
}
