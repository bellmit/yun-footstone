/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.exception;

import com.yunjia.footstone.auth.core.stp.StpUtil;
import com.yunjia.footstone.common.exception.BaseException;

/**
 * <p>
 * TODO 类注释
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 3:33 PM
 */
public class NotRoleException extends BaseException {

    private static final long serialVersionUID = 1685418036225927485L;

    private static final int NO_ROLE_ERROR_CODE = 4032;

    /** 角色标识 */
    private String role;

    /**
     * @return 获得角色标识
     */
    public String getRole() {
        return role;
    }

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

    public NotRoleException(String role) {
        this(role, StpUtil.stpLogic.loginType);
    }

    public NotRoleException(String role, String loginType) {
        super(NO_ROLE_ERROR_CODE, "无此角色：" + role);
        this.role = role;
        this.loginType = loginType;
    }
}
