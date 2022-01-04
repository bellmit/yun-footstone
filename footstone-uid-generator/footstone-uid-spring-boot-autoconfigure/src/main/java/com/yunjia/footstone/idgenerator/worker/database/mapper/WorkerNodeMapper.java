/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.idgenerator.worker.database.mapper;

import com.yunjia.footstone.idgenerator.worker.database.entity.WorkerNodeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * DAO for M_WORKER_NODE
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/28 1:41 PM
 */
@Repository
public interface WorkerNodeMapper {

    /**
     * Get {@link WorkerNodeEntity} by node host
     */
    WorkerNodeEntity getWorkerNodeByHostPort(@Param("host") String host, @Param("port") String port);

    /**
     * Add {@link WorkerNodeEntity}
     */
    void addWorkerNode(WorkerNodeEntity workerNodeEntity);
}
