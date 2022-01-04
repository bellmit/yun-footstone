/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.stp;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 对 {@link StpInterface} 接口默认的实现类
 * 如果开发者没有实现StpInterface接口，则使用此默认实现
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 2:23 PM
 */
public class DefaultStpInterfaceImpl implements StpInterface {

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return new ArrayList<String>();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return new ArrayList<String>();
    }
}
