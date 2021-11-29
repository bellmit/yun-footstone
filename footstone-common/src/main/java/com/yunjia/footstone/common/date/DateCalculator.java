/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.date;

import com.yunjia.footstone.common.util.Check;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *     日期计算类，基于joda-time进行日期计算
 * </p>
 *
 * @author sunkaiyun
 * @version 1.0
 * @date 2021-08-05 16:30
 */
public class DateCalculator {

    /**
     * 私有化构造
     */
    private DateCalculator() {
    }

    /**
     * 获得日期前几天或者后几天的日期
     *
     * @param date 指定的日期
     * @param days 前后的天数，为负数，则为前几天，正数则为后几天
     * @return
     */
    public static Date getDate(@Nonnull Date date, int days) {
        Objects.requireNonNull(date, "date must not null");
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.plusDays(days);
        return dateTime.toDate();
    }
    /**
     * 获取指定日期的当天第0秒
     *
     * @param date 指定的日期
     * @return
     */
    public static long getFirstMilliSecondOfDate(@Nonnull Date date) {
        Objects.requireNonNull(date, "date must not null");
        return getMilliSecondOfDate(date, 0, 0, 0, 0);
    }

    /**
     * 获取指定日期的当天最后一毫秒
     *
     * @param date 指定的日期
     * @return
     */
    public static long getLastMilliSecondOfDate(@Nonnull Date date) {
        Objects.requireNonNull(date, "date must not null");
        return getMilliSecondOfDate(date, 23, 59, 59, 999);
    }

    /**
     * 获取指定日期的当天的任意一毫秒
     *
     * @param date 指定的日期
     * @return
     */
    public static long getMilliSecondOfDate(@Nonnull Date date, int hourOfDay, int minuteOfHour, int secondOfMinute, int millisOfSecond) {
        Objects.requireNonNull(date, "date must not null");
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.withHourOfDay(hourOfDay).withMinuteOfHour(minuteOfHour).withSecondOfMinute(secondOfMinute).withMillisOfSecond(millisOfSecond);
        return dateTime.getMillis();
    }

    /**
     * 获取指定日期偏移多少周后的此周第一天
     * 注意：周一是一周的第一天
     *
     * @param date 指定日期为标准
     * @param week 偏移周数，正数：往后指定周数量，负数：往前指定周数量
     * @return
     */
    public static Date getFirstDayOfWeek(@Nonnull Date date, int week) {
        Objects.requireNonNull(date, "date must not null");
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.plusWeeks(week).dayOfWeek().withMinimumValue();
        return dateTime.toDate();
    }


    /**
     * 获取指定日期偏移多少周后的此周最后一天
     * 注意：周日是一周的最后一天
     *
     * @param date 指定日期为标准
     * @param week 偏移周数，正数：往后指定周数量，负数：往前指定周数量
     * @return
     */
    public static Date getLastDayOfWeek(@Nonnull Date date, int week) {
        Objects.requireNonNull(date, "date must not null");
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.plusWeeks(week).dayOfWeek().withMaximumValue();
        return dateTime.toDate();
    }

    /**
     * 获取指定日期当周的任意一天
     *
     *
     * @param date 指定日期为标准
     * @param week 偏移周数，正数：往后指定周数量，负数：往前指定周数量
     * @param dayOfWeek 指定天数 1第一天周一 7最后一天为周日
     * @return
     */
    public static Date getDayOfWeek(@Nonnull Date date, int week, int dayOfWeek) {
        Objects.requireNonNull(date, "date must not null");
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.plusWeeks(week).withDayOfWeek(dayOfWeek);
        return dateTime.toDate();
    }

    /**
     * 获取指定日期所在月份的前几月或者后几月的第一天日期
     *
     * @param date 指定日期所在月份为标准
     * @param month 偏移月数，正数：往后指定月数量，负数：往前指定月数量
     * @return
     */
    public static Date getFirstDayOfMonth(@Nonnull Date date, int month) {
        Objects.requireNonNull(date, "date must not null");
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.plusMonths(month).dayOfMonth().withMinimumValue();
        return dateTime.toDate();
    }

    /**
     * 获取指定日期所在月份的前几月或者后几月的最后一天日期
     *
     * @param date 指定日期所在月份为标准
     * @param month 偏移月数，正数：往后指定月数量，负数：往前指定月数量
     * @return
     */
    public static Date getLastDayOfMonth(@Nonnull Date date, int month) {
        Objects.requireNonNull(date, "date must not null");
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.plusMonths(month).dayOfMonth().withMaximumValue();
        return dateTime.toDate();
    }

    /**
     * 获取指定日期所在月份的前几月或者后几月任意一天
     *
     * @param date 指定日期所在月份为标准
     * @param month 偏移月数，正数：往后指定月数量，负数：往前指定月数量
     * @param dayOfMonth 指定天数 从1开始
     * @return
     */
    public static Date getDayOfMonth(@Nonnull Date date, int month, int dayOfMonth) {
        Objects.requireNonNull(date, "date must not null");
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.plusMonths(month).withDayOfMonth(dayOfMonth);
        return dateTime.toDate();
    }

    /**
     * 计算日期相隔多少天
     *
     * 计算两个日期之间相隔多少天，忽略时分秒,忽略日期前后顺序
     *  如：2016-09-04 23:59:59 至 2016-09-05 00:00:01 返回相隔1天
     *  跨月如：2016-09-26 23:59:59 至 2016-10-08 00:00:01 返回相隔12天
     * @param date1 日期1
     * @param date2 日期2
     * @return
     */
    public static int getBetweenDays(@Nonnull Date date1, @Nonnull Date date2) {
        Objects.requireNonNull(date1, "date1 must not null");
        Objects.requireNonNull(date2, "date2 must not null");
        LocalDate localDate1 = LocalDate.fromDateFields(date1);
        LocalDate localDate2 = LocalDate.fromDateFields(date2);
        return Math.abs(Days.daysBetween(localDate1, localDate2).getDays());
    }

    /**
     * 获取两个日期之间的所有日期
     * 忽略日期前后顺序，会自动判断两个日期前后顺序
     *
     *  例如：2016-9-30 23:59:59 至 2019-10-01 00:00:01 , yyyy-MM-dd
     * 返回值为 ["2016-9-30"]
     *
     * @param date1
     * @param date2
     * @return
     */
    public static List<Date> getDateBetweenDates(@Nonnull Date date1, @Nonnull Date date2) {
        Objects.requireNonNull(date1, "startDate must not null");
        Objects.requireNonNull(date2, "endDate must not null");
        Date startDate = date1;
        Date endDate = date2;
        //调整顺序
        if (date1.after(date2)) {
            startDate = date2;
            endDate = date1;
        }
        List<Date> dates = new ArrayList<>();
        long startmilliseconds = getFirstMilliSecondOfDate(startDate);
        long endmilliseconds = getLastMilliSecondOfDate(endDate);
        DateTime dateTime = new DateTime(startDate);
        while (startmilliseconds<endmilliseconds) {
            dates.add(dateTime.toDate());
            dateTime = dateTime.plusDays(1);
            startmilliseconds = getFirstMilliSecondOfDate(dateTime.toDate());
        }
        return dates;
    }

    /**
     * 获取两个日期之间的所有日期字符串，按照指定日期格式化
     * 忽略日期前后顺序，会自动判断两个日期前后顺序
     * 使用的是 DateTime.isBefore() 比较时间戳
     * 例如：2016-9-30 23:59:59 至 2019-10-01 00:00:01 , yyyy-MM-dd
     * 返回值为 ["2016-9-30","2019-10-01"]
     * @param date1
     * @param date2
     * @param pattern
     * @return
     */
    public static List<String> getDateStrBetweenDates(@Nonnull Date date1, @Nonnull Date date2, @Nonnull String pattern) {
        Objects.requireNonNull(date1, "startDate must not null");
        Objects.requireNonNull(date2, "endDate must not null");
        Objects.requireNonNull(pattern, "pattern must not null");
        Date startDate = date1;
        Date endDate = date2;
        //调整顺序
        if (date1.after(date2)) {
            startDate = date2;
            endDate = date1;
        }
        List<String> dates = new ArrayList<>();
        long startmilliseconds = getFirstMilliSecondOfDate(startDate);
        long endmilliseconds = getLastMilliSecondOfDate(endDate);
        DateTime dateTime = new DateTime(startDate);
        while (startmilliseconds<endmilliseconds) {
            dates.add(DateFormatter.format(dateTime.getMillis(), pattern));
            dateTime = dateTime.plusDays(1);
            startmilliseconds = getFirstMilliSecondOfDate(dateTime.toDate());
        }
        return dates;
    }


    /**
     * 计算两个时间段间隔里面按照固定间隔返回时间点集合
     * <p/>
     * 这里会 startDate 到 endDate 最后endDate如果相等会算入集合
     * 如：[2016-10-20 12:00:00.000 -2016-10-20 13:00:00.000,'yyyy-MM-dd HH:mm',30]
     * 返回：["2016-10-20 12:00","2016-10-20 12:30","2016-10-20 13:00"]
     * @param date1
     * @param date2
     * @param minutesSplit 间隔多少分
     * @param pattern
     * @return
     */
    public static List<String> getTimePointBetweenDates(@Nonnull Date date1, @Nonnull Date date2, @Nonnull String pattern, int minutesSplit) {
        Objects.requireNonNull(date1, "startDate must not null");
        Objects.requireNonNull(date2, "endDate must not null");
        Objects.requireNonNull(pattern, "pattern must not null");
        Date startDate = date1;
        Date endDate = date2;
        //调整顺序
        if (date1.after(date2)) {
            startDate = date2;
            endDate = date1;
        }
        DateTime startDateTime = new DateTime(startDate);
        DateTime endDateTime = new DateTime(endDate);
        List<String> points = new ArrayList<>();
        while (startDateTime.isBefore(endDateTime.getMillis()) || startDateTime.getMillis() == endDateTime.getMillis()) {
            points.add(DateFormatter.format(startDateTime.getMillis(), pattern));
            startDateTime = startDateTime.plusMinutes(minutesSplit);
        }
        return points;
    }
    /**
     * 是否同一天 年月日是否一致
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isTheSameDay(@Nonnull Date date1, @Nonnull Date date2) {
        if(Check.isNullObjects(date1,date2)){
            return false;
        }
        DateTime dateTime1 = new DateTime(date1);
        DateTime dateTime2 = new DateTime(date2);
        //年份是否一样
        if (dateTime1.getYear() != dateTime2.getYear()) {
            return false;
        }
        //日期是否一样
        if (dateTime1.getDayOfYear() != dateTime2.getDayOfYear()) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否同一天 年月日是否一致
     *
     * @param dateStr1
     * @param pattern1
     * @param dateStr2
     * @param pattern2
     * @return
     */
    public static boolean isTheSameDay(@Nonnull String dateStr1, @Nonnull String pattern1, @Nonnull String dateStr2, @Nonnull String pattern2) {
        if(Check.isNullObjects(dateStr1, dateStr2)){
            return false;
        }
        return isTheSameDay(DateParser.parse(dateStr1, pattern1), DateParser.parse(dateStr2, pattern2));
    }


    /**
     * 判断日期date 是否在日期dateStr1/dateStr2内
     *
     * @param dateStr1
     * @param pattern1
     * @param dateStr2
     * @param pattern2
     * @return
     */
    public static boolean isInBetweenDate(@Nonnull String dateStr1, @Nonnull String pattern1, @Nonnull String dateStr2, @Nonnull String pattern2, Date date) {
        Objects.requireNonNull(date, "date must not null");
        Objects.requireNonNull(pattern1, "date pattern1 must not null");
        Objects.requireNonNull(pattern2, "date pattern2 must not null");
        if(Check.isNullObjects(dateStr1, dateStr2)){
            return false;
        }
        Date date1 = DateParser.parse(dateStr1, pattern1);
        Date date2 = DateParser.parse(dateStr2, pattern2);
        return (date1.getTime()<date.getTime() && date.getTime()<date2.getTime()) || (date1.getTime()>date.getTime() && date.getTime()>date2.getTime());
    }

    /**
     * 获取当前时间秒数量
     *
     * @return
     */
    public static int currentSecond(){
        return Long.valueOf(System.currentTimeMillis() / 1000).intValue();
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param strDate
     *
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算指定日期之前多少分钟
     * @param date
     * @param minute
     * @return
     */
    public static Date getBeforeMinuteDate(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, -1*Math.abs(minute));
        return calendar.getTime();
    }

    /**
     * 计算指定日期之前多少小时
     * @param date
     * @param hour
     * @return
     */
    public static Date getBeforeHourDate(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, -1*Math.abs(hour));
        return calendar.getTime();
    }
}
