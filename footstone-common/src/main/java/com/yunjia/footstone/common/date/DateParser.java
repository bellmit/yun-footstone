/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.date;

import com.yunjia.footstone.common.util.Check;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

/**
 * <p>
 *     解析各种Date字符成为JDK Date类型
 *     基于joda-time解析
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 16:33
 */
public class DateParser {

    /**
     * 私有化构造
     */
    private DateParser(){

    }

    /**
     * 按照默认的日期格式(yyyy-MM-dd)转换为日期
     *
     * @param dateStr
     * @return
     */
    public static Date parseDate(@Nonnull String dateStr) {
        return parse(dateStr, DatePattern.DEFAULT_FORMAT_DATE_PATTERN);
    }


    /**
     * 按照默认的日期格式(yyyy-MM-dd HH:mm:ss)转换为日期
     *
     * @param dateStr
     * @return
     */
    public static Date parseDateTime(@Nonnull String dateStr) {
        return parse(dateStr, DatePattern.DEFAULT_FORMAT_DATETIME_PATTERN);
    }


    /**
     * 按照指定格式匹配解析字符串为日期
     * 如果需要解析的字符串为空或者null 则返回的null
     * @param dateStr
     * @param pattern
     * @return
     */
    public static
    @Nullable
    Date parse(@Nonnull String dateStr, @Nonnull String pattern) {
        return parse(dateStr, pattern, TimeZone.getTimeZone("Etc/GMT-8"));
    }

    /**
     * 按照指定格式匹配解析字符串为日期
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date parse(@Nonnull String dateStr, @Nonnull String pattern, TimeZone timeZone) {
        if (Check.isNullOrEmpty(dateStr)) {
            return null;
        }
        Objects.requireNonNull(pattern, "date pattern must not null");
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(pattern).withZone(DateTimeZone.forTimeZone(timeZone));
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateStr);
        return dateTime.toDate();
    }
}
