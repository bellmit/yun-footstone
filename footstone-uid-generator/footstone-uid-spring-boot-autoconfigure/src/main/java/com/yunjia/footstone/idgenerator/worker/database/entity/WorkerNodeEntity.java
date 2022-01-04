/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.idgenerator.worker.database.entity;

import com.yunjia.footstone.idgenerator.worker.WorkerNodeType;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>
 * Entity for M_WORKER_NODE
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/28 1:42 PM
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class WorkerNodeEntity implements Serializable {

    private static final long serialVersionUID = -54567961265454014L;

    /**
     * Entity unique id (table unique)
     */
    private long id;

    /**
     * Type of CONTAINER: HostName, ACTUAL : IP.
     */
    private String hostName;

    /**
     * Type of CONTAINER: Port, ACTUAL : Timestamp + Random(0-10000)
     */
    private String port;

    /**
     * type of {@link WorkerNodeType}
     */
    private int type;

    /**
     * Worker launch date, default now
     */
    private Date launchDate = new Date();

    /**
     * Created time
     */
    private Date createTime;

    /**
     * Last modified
     */
    private Date updateTime;

}
