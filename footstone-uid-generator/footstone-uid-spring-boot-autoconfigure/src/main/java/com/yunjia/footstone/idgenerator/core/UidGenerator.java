/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.idgenerator.core;

import com.yunjia.footstone.idgenerator.exception.UidGenerateException;

/**
 * <p>
 * Represents a unique id generator.
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/18 8:35 PM
 */
public interface UidGenerator {

    /**
     * Get a unique ID
     *
     * @return UID
     * @throws UidGenerateException
     */
    long getUID() throws UidGenerateException;

    /**
     * Parse the UID into elements which are used to generate the UID. <br>
     * Such as timestamp & workerId & sequence...
     *
     * @param uid
     * @return Parsed info
     */
    String parseUID(long uid);
}
