/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.aggregate;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.esotericsoftware.kryo.Kryo;

/**
 * <p>
 * 基于kryo的深度拷贝
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/13 3:52 PM
 */
public class KryoDeepCopier implements DeepCopier {

    private static final ThreadLocal<Kryo> KRYO_THREAD_LOCAL = new TransmittableThreadLocal<>();

    /**
     * 实体深拷贝方法
     */
    @Override
    public <T> T deepClone(T object) {
        Kryo kryo = KRYO_THREAD_LOCAL.get();
        if(null == kryo) {
            KRYO_THREAD_LOCAL.set(new Kryo());
        }
        return kryo.copy(object);
    }
}
