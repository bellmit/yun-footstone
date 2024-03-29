/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.idgenerator.properties;

import com.yunjia.footstone.idgenerator.buffer.RingBuffer;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * CachedUid实现所需参数
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/18 4:55 PM
 */
@Data
@ConfigurationProperties(value = "yunjia.footstone.uid.cache")
public class CachedUidProperties {

    private static final int DEFAULT_BOOST_POWER = 3;

    /**
     * RingBuffer size扩容参数, 可提高UID生成的吞吐量.
     * 默认:3， 原bufferSize=8192, 扩容后bufferSize= 8192 << 3 = 65536
     */
    private int boostPower = DEFAULT_BOOST_POWER;

    /**
     * 指定何时向RingBuffer中填充UID, 取值为百分比(0, 100), 默认为50
     * 举例: bufferSize=1024, paddingFactor=50 -> threshold=1024 * 50 / 100 = 512.
     * 当环上可用UID数量 < 512时, 将自动对RingBuffer进行填充补全
     */
    private int paddingFactor = RingBuffer.DEFAULT_PADDING_PERCENT;

    /**
     * 另外一种RingBuffer填充时机, 在Schedule线程中, 周期性检查填充
     * 默认:不配置此项, 即不实用Schedule线程. 如需使用, 请指定Schedule线程时间间隔, 单位:秒
     */
    private Long scheduleInterval;
}
