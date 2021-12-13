/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.aggregate;

/**
 * <p>
 * 深拷贝，用于创建聚合根的快照
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/10 4:24 PM
 */
public interface DeepCopier {

    /**
     * 实体深拷贝方法
     */
    <T> T deepClone(T object);
}
