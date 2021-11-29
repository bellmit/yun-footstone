/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.idgenerator.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * ID生成器配置文件
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/17 6:53 PM
 */
@Data
@ConfigurationProperties(value = "yunjia.footstone.uid.base")
public class BaseUidProperties {

    /**
     * 时间占用bit
     */
    protected int timeBits = 28;

    /**
     * workerId占用bit
     */
    protected int workerBits = 22;

    /**
     * 序列号占用bit
     */
    protected int seqBits = 13;

    /**
     * 自定义时间戳开始日期
     */
    protected String epochStr = "2021-11-20";


}
