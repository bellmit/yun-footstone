/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.auth.core.context.model;

import cn.hutool.core.util.StrUtil;
import com.yunjia.footstone.auth.core.exception.AuthException;

/**
 * <p>
 * Request 包装类
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/24 6:40 PM
 */
public interface AuthRequest {

    /**
     * 获取底层源对象
     *
     * @return see note
     */
    Object getSource();

    /**
     * 在 [请求体] 里获取一个值
     *
     * @param name 键
     *
     * @return 值
     */
    String getParam(String name);

    /**
     * 在 [请求体] 里获取一个值，值为空时返回默认值
     *
     * @param name         键
     * @param defaultValue 值为空时的默认值
     *
     * @return 值
     */
    default String getParam(String name, String defaultValue) {
        String value = getParam(name);
        if(StrUtil.isBlank(value)) {
            return defaultValue;
        }
        return value;
    }

    /**
     * 检测提供的参数是否为指定值
     *
     * @param name  键
     * @param value 值
     *
     * @return 是否相等
     */
    default boolean isParam(String name, String value) {
        String paramValue = getParam(name);
        return StrUtil.isNotBlank(paramValue) && paramValue.equals(value);
    }

    /**
     * 检测请求是否提供了指定参数
     *
     * @param name 参数名称
     *
     * @return 是否提供
     */
    default boolean hasParam(String name) {
        return StrUtil.isNotBlank(getParam(name));
    }

    /**
     * 在 [请求体] 里获取一个值 （此值必须存在，否则抛出异常 ）
     *
     * @param name 键
     *
     * @return 参数值
     */
    default String getParamNotNull(String name) {
        String paramValue = getParam(name);
        if(StrUtil.isBlank(paramValue)) {
            throw new AuthException("缺少参数：" + name);
        }
        return paramValue;
    }


    /**
     * 在 [请求头] 里获取一个值
     *
     * @param name 键
     *
     * @return 值
     */
    String getHeader(String name);

    /**
     * 在 [请求头] 里获取一个值
     *
     * @param name         键
     * @param defaultValue 值为空时的默认值
     *
     * @return 值
     */
    default String getHeader(String name, String defaultValue) {
        String value = getHeader(name);
        if(StrUtil.isBlank(value)) {
            return defaultValue;
        }
        return value;
    }

    /**
     * 在 [Cookie作用域] 里获取一个值
     *
     * @param name 键
     *
     * @return 值
     */
    String getCookieValue(String name);

    /**
     * 返回当前请求path (不包括上下文名称)
     *
     * @return see note
     */
    String getRequestPath();

    /**
     * 返回当前请求path是否为指定值
     *
     * @param path path
     *
     * @return see note
     */
    default boolean isPath(String path) {
        return getRequestPath().equals(path);
    }

    /**
     * 返回当前请求的url，不带query参数，例：http://xxx.com/test
     *
     * @return see note
     */
    String getUrl();

    /**
     * 返回当前请求的类型
     *
     * @return see note
     */
    String getMethod();

    /**
     * 此请求是否为Ajax请求
     *
     * @return see note
     */
    default boolean isAjax() {
        return getHeader("X-Requested-With") != null;
    }

    /**
     * 转发请求
     *
     * @param path 转发地址
     *
     * @return 任意值
     */
    default Object forward(String path) {
        throw new AuthException("No implementation");
    }
}
