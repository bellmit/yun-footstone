/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.contxt;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import java.util.Optional;

/**
 * <p>
 * 线程上下文
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 10:57 AM
 */
public class LocalContextHolder {

    private static final ThreadLocal<LocalContext> NAMED_THREAD_LOCAL = new TransmittableThreadLocal<>();

    private LocalContextHolder() {
    }

    public static <T extends LocalContext> T get(Class<T> clazz) {
        return (T) get();
    }

    public static <T extends LocalContext> T get() {
        if(NAMED_THREAD_LOCAL.get() == null) {
            newInstance();
        }
        return (T) NAMED_THREAD_LOCAL.get();
    }

    public static <T extends LocalContext> void set(T context) {
        if(context == null) {
            remove();
        } else {
            NAMED_THREAD_LOCAL.set(context);
        }
    }

    /**
     * 先拿到当前资源对象调用clear方法，再清理当前ThreadLocal资源
     */
    public static void clear() {
        Optional.ofNullable(NAMED_THREAD_LOCAL.get()).ifPresent(ContextClear::clear);
        NAMED_THREAD_LOCAL.remove();
    }

    /**
     * 清理当前ThreadLocal资源
     */
    public static void remove() {
        NAMED_THREAD_LOCAL.remove();
    }

    private static void newInstance() {
        if(ObjectUtil.isNull(NAMED_THREAD_LOCAL.get())) {
            NAMED_THREAD_LOCAL.set(new LocalContext());
        }
    }
}
