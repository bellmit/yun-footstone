/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.oauth2.exception;

import com.yunjia.footstone.common.exception.BaseException;

/**
 * <p>
 * 代表OAuth2认证流程错误
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/30 8:53 AM
 */
public class OAuth2Exception extends BaseException {

    private static final long serialVersionUID = 4253810326685489183L;

    private static final int OAUTH2_ERROR_CODE = 4038;

    /**
     * 一个异常：代表OAuth2认证流程错误
     *
     * @param message 异常描述
     */
    public OAuth2Exception(String message) {
        super(OAUTH2_ERROR_CODE, message);
    }

    /**
     * 如果flag==true，则抛出message异常
     *
     * @param flag    标记
     * @param message 异常信息
     */
    public static void throwBy(boolean flag, String message) {
        if(flag) {
            throw new OAuth2Exception(message);
        }
    }
}
