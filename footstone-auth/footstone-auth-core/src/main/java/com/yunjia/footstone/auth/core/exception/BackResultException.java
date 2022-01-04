/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.exception;

import com.yunjia.footstone.common.exception.BaseException;

/**
 * <p>
 * 代表停止匹配，直接退出，向前端输出结果
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/29 3:39 PM
 */
public class BackResultException extends BaseException {

    private static final long serialVersionUID = 2366768191794902874L;

    private static final int BACK_RESULT_ERROR_CODE = 4037;

    /**
     * 要输出的结果
     */
    public Object result;

    /**
     * 构造
     * @param result 要输出的结果
     */
    public BackResultException(Object result) {
        super(BACK_RESULT_ERROR_CODE, String.valueOf(result));
        this.result = result;
    }
}
