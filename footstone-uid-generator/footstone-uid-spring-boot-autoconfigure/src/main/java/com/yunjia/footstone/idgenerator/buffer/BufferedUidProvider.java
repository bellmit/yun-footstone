/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.idgenerator.buffer;

import java.util.List;

/**
 * <p>
 * Buffered UID provider(Lambda supported), which provides UID in the same one second
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/26 5:41 PM
 */
@FunctionalInterface
public interface BufferedUidProvider {

    /**
     * Provides UID in one second
     */
    List<Long> provide(long momentInSecond);
}
