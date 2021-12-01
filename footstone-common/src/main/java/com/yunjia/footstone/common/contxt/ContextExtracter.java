/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.contxt;

import java.util.Collection;
import java.util.Map;

/**
 * <p>
 * 属性提取
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 11:08 AM
 */
public interface ContextExtracter {

    /**
     * Header extract
     *
     * @return headers
     */
    Map<String, Collection<String>> extract();
}
