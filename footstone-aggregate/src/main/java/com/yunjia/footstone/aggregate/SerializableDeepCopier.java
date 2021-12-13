/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.aggregate;

import java.io.Serializable;
import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.SerializationUtils;

/**
 * <p>
 * Java序列化方式深拷贝
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/13 3:37 PM
 */
public class SerializableDeepCopier implements DeepCopier {

    /**
     * 实体深拷贝方法
     */
    @Override
    public <T> T deepClone(T object) {
        if (object instanceof Serializable) {
            try {
                return (T) SerializationUtils.clone((Serializable) object);
            } catch (SerializationException exception) {
                throw new IllegalArgumentException(String.format("%s should be a serializable object.", object.getClass().getName()), exception);
            }
        }
        throw new IllegalArgumentException(String.format("%s should be a serializable object.", object.getClass().getName()));
    }
}
