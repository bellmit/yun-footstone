package com.yun.footstone.common.util;

/**
 * <p>
 *     Bean复制回调函数
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 17:15
 */
@FunctionalInterface
public interface BeanCopyUtilCallBack<S, T> {

    /**
     * 定义默认回调方法
     * @param t
     * @param s
     */
    void callBack(S s, T t);
}
