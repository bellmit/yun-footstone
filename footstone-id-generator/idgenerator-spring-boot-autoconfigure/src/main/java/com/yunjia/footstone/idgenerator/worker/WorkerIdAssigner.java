/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.idgenerator.worker;

import com.yunjia.footstone.idgenerator.core.impl.DefaultUidGenerator;

/**
 * <p>
 * Represents a worker id assigner for {@link DefaultUidGenerator}
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/26 6:15 PM
 */
public interface WorkerIdAssigner {

    /**
     * Assign worker id for {@link DefaultUidGenerator}
     *
     * @return assigned worker id
     */
    long assignWorkerId();
}
