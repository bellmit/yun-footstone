/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.aggregate;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 聚合根基类
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/10 3:11 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseRoot implements Serializable {

    private static final long serialVersionUID = -959302148735550082L;

    /**
     * 当聚合根中的实体为新建时，聚合根的初始版本号，代表为新建实体
     */
    public static final int ROOT_NEW_VERSION = 0;

    /**
     * 聚合根版本号
     */
    private Integer rootVersion;
}
