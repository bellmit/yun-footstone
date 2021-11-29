/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.idgenerator.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * Represents a padded {@link AtomicLong} to prevent the FalseSharing problem<p>
 * <p>
 * The CPU cache line commonly be 64 bytes, here is a sample of cache line after padding:<br>
 * 64 bytes = 8 bytes (object reference) + 6 * 8 bytes (padded long) + 8 bytes (a long value)
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/26 5:51 PM
 */
public class PaddedAtomicLong extends AtomicLong {

    private static final long serialVersionUID = 5605146669773345636L;

    /**
     * Padded 6 long (48 bytes)
     */
    public volatile long p1, p2, p3, p4, p5, p6 = 7L;

    /**
     * Constructors from {@link AtomicLong}
     */
    public PaddedAtomicLong() {
        super();
    }

    public PaddedAtomicLong(long initialValue) {
        super(initialValue);
    }

    /**
     * To prevent GC optimizations for cleaning unused padded references
     */
    public long sumPaddingToPreventOptimization() {
        return p1 + p2 + p3 + p4 + p5 + p6;
    }
}
