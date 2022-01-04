/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.stp;

import java.util.List;

/**
 * <p>
 * 权限认证接口，实现此接口即可集成权限认证功能
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 2:19 PM
 */
public interface StpInterface {

    /**
     * 返回指定账号id所拥有的权限码集合
     *
     * @param loginId   账号id
     * @param loginType 账号类型
     *
     * @return 该账号id具有的权限码集合
     */
    List<String> getPermissionList(Object loginId, String loginType);

    /**
     * 返回指定账号id所拥有的角色标识集合
     *
     * @param loginId   账号id
     * @param loginType 账号类型
     *
     * @return 该账号id具有的角色标识集合
     */
    List<String> getRoleList(Object loginId, String loginType);
}
