/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.exception;

import com.yunjia.footstone.common.exception.BaseException;

/**
 * <p>
 * 账号已被封禁
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 3:01 PM
 */
public class DisableLoginException extends BaseException {

    private static final long serialVersionUID = 1429748489786587860L;

    private static final int DISABLE_LOGIN_ERROR_CODE = 4031;

    /** 异常标记值 */
    public static final String DISABLE_LOGIN_VALUE = "disable";

    /**
     * 异常提示语
     */
    public static final String DISABLE_LOGIN_MESSAGE = "此账号已被封禁";

    /**
     * 账号类型
     */
    private String loginType;

    /**
     * 被封禁的账号id
     */
    private Object loginId;

    /**
     * 封禁剩余时间，单位：秒
     */
    private long disableTime;

    /**
     * Constructor with message & cause
     */
    public DisableLoginException(String message, Throwable cause) {
        super(DISABLE_LOGIN_ERROR_CODE, message, cause);
    }

    /**
     * Constructor with message
     */
    public DisableLoginException(String message) {
        super(DISABLE_LOGIN_ERROR_CODE, message);
    }

    /**
     * Constructor with message format
     */
    public DisableLoginException(String msgFormat, Object... args) {
        super(DISABLE_LOGIN_ERROR_CODE, String.format(msgFormat, args));
    }

    /**
     * Constructor with cause
     */
    public DisableLoginException(Throwable cause) {
        super(DISABLE_LOGIN_ERROR_CODE, cause);
    }

    /**
     * 获取账号类型
     *
     * @return See Note
     */
    public String getLoginType() {
        return loginType;
    }

    /**
     * 获取: 被封禁的账号id
     *
     * @return See above
     */
    public Object getLoginId() {
        return loginId;
    }

    /**
     * 获取: 封禁剩余时间，单位：秒
     *
     * @return See above
     */
    public long getDisableTime() {
        return disableTime;
    }

    /**
     * 一个异常：代表账号已被封禁
     *
     * @param loginType   账号类型
     * @param loginId     被封禁的账号id
     * @param disableTime 封禁剩余时间，单位：秒
     */
    public DisableLoginException(String loginType, Object loginId, long disableTime) {
        super(DISABLE_LOGIN_ERROR_CODE, DISABLE_LOGIN_MESSAGE);
        this.loginId = loginId;
        this.loginType = loginType;
        this.disableTime = disableTime;
    }
}
