/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.exception;

import cn.hutool.core.util.ObjectUtil;
import com.yunjia.footstone.common.exception.BaseException;

/**
 * <p>
 * 权限相关异常
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/23 8:04 PM
 */
public class AuthException extends BaseException {

    private static final long serialVersionUID = -6866515210452709991L;

    private static final int NO_PERMISSIONS_ERROR_CODE = 403;

    /**
     * Constructor with message & cause
     */
    public AuthException(String message, Throwable cause) {
        super(NO_PERMISSIONS_ERROR_CODE, message, cause);
    }

    /**
     * Constructor with message
     */
    public AuthException(String message) {
        super(NO_PERMISSIONS_ERROR_CODE, message);
    }

    /**
     * Constructor with message format
     */
    public AuthException(String msgFormat, Object... args) {
        super(NO_PERMISSIONS_ERROR_CODE, String.format(msgFormat, args));
    }

    /**
     * Constructor with cause
     */
    public AuthException(Throwable cause) {
        super(NO_PERMISSIONS_ERROR_CODE, cause);
    }

    /**
     * 如果flag==true，则抛出message异常
     * @param flag 标记
     * @param message 异常信息
     */
    public static void throwBy(boolean flag, String message) {
        if(flag) {
            throw new AuthException(message);
        }
    }

    /**
     * 如果value==null或者isEmpty，则抛出message异常
     * @param value 值
     * @param message 异常信息
     */
    public static void throwByNull(Object value, String message) {
        if(ObjectUtil.isEmpty(value)) {
            throw new AuthException(message);
        }
    }
}
