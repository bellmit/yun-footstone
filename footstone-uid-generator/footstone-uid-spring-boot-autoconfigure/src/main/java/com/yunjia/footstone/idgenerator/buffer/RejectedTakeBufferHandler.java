/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.idgenerator.buffer;

/**
 * <p>
 * If cursor catches the tail it means that the ring buffer is empty, any more buffer take request will be rejected.
 * Specify the policy to handle the reject. This is a Lambda supported interface
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/26 5:43 PM
 */
@FunctionalInterface
public interface RejectedTakeBufferHandler {

    /**
     * Reject take buffer request
     */
    void rejectTakeBuffer(RingBuffer ringBuffer);
}
