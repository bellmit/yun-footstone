/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.aggregate;

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

    private Kryo kryo = new Kryo();

    /**
     * 实体深拷贝方法
     */
    @Override
    public <T> T deepClone(T object) {
        return kryo.copy(object);
    }
}
