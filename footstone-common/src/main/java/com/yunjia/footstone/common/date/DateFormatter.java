/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.date;

import com.yunjia.footstone.common.util.Check;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 *     日期格式化，基于joda-time实现，对外暴露统一为JDK Date，提供
 *     1、基于JDK日期Date格式化
 *     2、基于时间戳格式化
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 16:34
 */
public class DateFormatter {

    /**
     * 私有化构造
     */
    private DateFormatter() {
    }

    /**
     * 格式化日期
     * <p/>
     * 返回日期格式为：yyyy-MM-dd
     *
     * @param date
     * @return date
     */
    public static String formatDate(@Nullable Date date) {
        return format(date, DatePattern.DEFAULT_FORMAT_DATE_PATTERN);
    }

    /**
     * 格式化日期
     * <p/>
     * 返回日期格式为：yyyy-MM-dd
     *
     * @param milliseconds 毫秒
     * @return date
     */
    public static String formatDate(long milliseconds) {
        return format(milliseconds, DatePattern.DEFAULT_FORMAT_DATE_PATTERN);
    }

    /**
     * 格式化日期
     * <p/>
     * 返回日期格式为：yyyy-MM-dd HH:mm:ss
     * 24小时制
     *
     * @param date
     * @return
     */
    public static String formatDateTime(@Nullable Date date) {
        return format(date, DatePattern.DEFAULT_FORMAT_DATETIME_PATTERN);
    }

    /**
     * 格式化日期
     * <p/>
     * 返回日期格式为：yyyy-MM-dd HH:mm:ss
     * 24小时制
     *
     * @param milliseconds 毫秒
     * @return
     */
    public static String formatDateTime(long milliseconds) {
        return format(milliseconds, DatePattern.DEFAULT_FORMAT_DATETIME_PATTERN);
    }

    /**
     * 按照指定格式，格式化日期
     * 如果需要格式化的日期为空，返回空字符串
     *
     * <p/>
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(@Nullable Date date, @NotNull String pattern) {
        if (Check.isNull(date)) {
            return "";
        }
        Objects.requireNonNull(pattern, "date pattern must not null");
        return format(date.getTime(), pattern);
    }

    /**
     * 格式化日期
     * <p/>
     * 返回日期格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param milliseconds 毫秒
     * @return
     */
    public static String format(long milliseconds, @NotNull String pattern) {
        Objects.requireNonNull(pattern, "date pattern must not null");
        DateTime dateTime = new DateTime(milliseconds);
        return dateTime.toString(pattern);
    }
}
