/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.exception;

import com.yunjia.footstone.auth.core.stp.StpUtil;
import com.yunjia.footstone.common.exception.BaseException;

/**
 * <p>
 * 代表会话未能通过权限认证
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 3:36 PM
 */
public class NotPermissionException extends BaseException {

    private static final long serialVersionUID = 6728690430923749798L;

    private static final int NO_PERMISSION_ERROR_CODE = 4033;

    /**
     * 账号类型
     */
    private String loginType;

    /**
     * 获得账号类型
     *
     * @return 账号类型
     */
    public String getLoginType() {
        return loginType;
    }

    public NotPermissionException(String code) {
        this(code, StpUtil.stpLogic.loginType);
    }

    public NotPermissionException(String code, String loginType) {
        super(NO_PERMISSION_ERROR_CODE, "无此权限：" + code);
        this.loginType = loginType;
    }
}
