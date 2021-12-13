/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.aggregate;

import com.yunjia.footstone.aggregate.deepequals.DeepEquals;

/**
 * <p>
 * JavaUtilDeepComparator use deepEquals, which is based on https://github.com/jdereg/java-util, to implement the DeepComparator interface.
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/13 3:38 PM
 */
public class JavaUtilDeepComparator implements DeepComparator {

    /**
     * 比较方法
     */
    @Override
    public <T> boolean deepEquals(T a, T b) {
        return new DeepEquals().isDeepEquals(a, b);
    }
}
