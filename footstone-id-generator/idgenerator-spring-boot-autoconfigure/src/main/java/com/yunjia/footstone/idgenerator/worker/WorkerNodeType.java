/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.idgenerator.worker;

import com.yunjia.footstone.idgenerator.utils.ValuedEnum;

/**
 * <p>
 * WorkerNodeType
 * <li>CONTAINER: Such as Docker
 * <li>ACTUAL: Actual machine
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/26 6:25 PM
 */
public enum WorkerNodeType implements ValuedEnum<Integer> {

    CONTAINER(1),
    ACTUAL(2);

    /**
     * Lock type
     */
    private final Integer type;

    /**
     * Constructor with field of type
     */
    WorkerNodeType(Integer type) {
        this.type = type;
    }

    @Override
    public Integer value() {
        return type;
    }

}
