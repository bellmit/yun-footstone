/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.idgenerator.buffer;

/**
 * <p>
 * If tail catches the cursor it means that the ring buffer is full, any more buffer put request will be rejected.
 * Specify the policy to handle the reject. This is a Lambda supported interface
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/26 5:42 PM
 */
@FunctionalInterface
public interface RejectedPutBufferHandler {

    /**
     * Reject put buffer request
     */
    void rejectPutBuffer(RingBuffer ringBuffer, long uid);
}
