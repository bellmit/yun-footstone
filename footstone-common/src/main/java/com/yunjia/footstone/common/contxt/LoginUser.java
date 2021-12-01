/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.contxt;

/**
 * <p>
 * 登录用户接口
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 11:03 AM
 */
public interface LoginUser {

    /**
     * 获取用户编号
     */
    String getUserCode();

    /**
     * 获取用户昵称
     */
    String getNickname();

    /**
     * 获取用户类型
     */
    Integer getUserType();
}
