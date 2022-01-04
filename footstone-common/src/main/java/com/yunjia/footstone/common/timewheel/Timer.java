/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.timewheel;

/**
 * <p>
 * 时间轮接口定义
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/22 1:41 PM
 */
public interface Timer {

    /**
     * 添加一个新任务
     */
    void add(TimerTask timerTask);

    /**
     * 驱动时间
     */
    boolean advanceClock(Long timeoutMs) throws Exception;

    /**
     * 等待执行的任务数
     */
    int size();

    /**
     * 停止时间轮，未执行的任务将无法被执行
     */
    void shutdown();
}
