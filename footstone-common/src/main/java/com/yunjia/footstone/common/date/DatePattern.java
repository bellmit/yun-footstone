/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.date;

/**
 * <p>
 *     日期格式
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 15:37
 */
public class DatePattern {

    private DatePattern() {

    }

    /**
     * 日期
     */
    public static final String DEFAULT_FORMAT_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 时间 24小时制
     */
    public static final String DEFAULT_FORMAT_HOUR_PATTERN = "yyyy-MM-dd HH";

    /**
     * 分钟
     */
    public static final String DEFAULT_FORMAT_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";

    /**
     * 秒
     */
    public static final String DEFAULT_FORMAT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 毫秒
     */
    public static final String DEFAULT_FORMAT_MILLISECOND_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * ISO-8601
     */
    public static final String DEFAULT_ISO8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
}
