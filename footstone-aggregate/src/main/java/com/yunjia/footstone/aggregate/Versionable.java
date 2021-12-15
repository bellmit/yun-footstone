/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.aggregate;

import java.io.Serializable;

/**
 * <p>
 * The aggregate use version to manage optimistic lock
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/10 3:11 PM
 */
public interface Versionable extends Serializable {

    /**
     * 当聚合根中的实体为新建时，聚合根的初始版本号，代表为新建实体
     */
    int ROOT_NEW_VERSION = 0;

    /**
     * 聚合根版本号
     */
    Integer getRootVersion();
}
