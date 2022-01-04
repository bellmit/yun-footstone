/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.basic;

/**
 * <p>
 * Http Basic 认证 Util
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/27 7:25 PM
 */
public class BasicUtil {

    /**
     * 底层 SaBasicTemplate 对象
     */
    public static BasicTemplate basicTemplate = new BasicTemplate();

    /**
     * 获取浏览器提交的 Basic 参数 （裁剪掉前缀并解码）
     *
     * @return 值
     */
    public static String getAuthorizationValue() {
        return basicTemplate.getAuthorizationValue();
    }

    /**
     * 对当前会话进行 Basic 校验（使用全局配置的账号密码），校验不通过则抛出异常
     */
    public static void check() {
        basicTemplate.check();
    }

    /**
     * 对当前会话进行 Basic 校验（手动设置账号密码），校验不通过则抛出异常
     *
     * @param account 账号（格式为 user:password）
     */
    public static void check(String account) {
        basicTemplate.check(account);
    }

    /**
     * 对当前会话进行 Basic 校验（手动设置 Realm 和 账号密码），校验不通过则抛出异常
     *
     * @param realm   领域
     * @param account 账号（格式为 user:password）
     */
    public static void check(String realm, String account) {
        basicTemplate.check(realm, account);
    }

}
