/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.algorithms;

import com.google.common.base.Joiner;
import com.yunjia.footstone.common.util.Check;

/**
 * <p>
 *     TODO：类功能描述
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 17:32
 */
public class SnowFlakeIDGenerator extends AbstractIDGenerator {

    /**
     * 定位时间点 2021-08-05
     */
    private long positionTime = 1628156268923L;

    /**
     * 机器id占4位  1111  最大 2^4-1 = 15 最多支持15台id生成机器集群
     */
    private int workerBits = 4;

    /**
     * 序列号占6位 111111 最大 2^6-1 = 63 支持每个机器每毫秒63个订单
     */
    private int sequenceBits = 6;

    /**
     * 序列号掩码
     * 000000000000 000000000000 000000000000 000000000000 0000000000 111111
     */
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 机器id最大数量 15
     */
    private long maxWorkerId = -1L ^ (-1L << workerBits);

    //机器id向左移6位
    private long workerIdLeftShift = sequenceBits;

    //时间截向左移6+4=10位
    private long timestampLeftShift = workerBits + workerIdLeftShift;


    private long lastTimestamp = -1L;

    private long sequence = 0;


    public SnowFlakeIDGenerator(String identity, int workerId) {
        super(identity, workerId);
        if (workerId < 0 || workerId > maxWorkerId) {
            throw new IllegalArgumentException(String.format("workId cannot be greater than %d or less than 0", maxWorkerId));
        }
    }

    @Override
    public synchronized String nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;
        long id = ((timestamp - positionTime) << timestampLeftShift) | (getWorkerId() << workerIdLeftShift) | sequence;
        if (Check.isNullOrEmpty(getIdentity())) {
            return String.valueOf(id);
        }
        return Joiner.on("").join(getIdentity(), id);
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
