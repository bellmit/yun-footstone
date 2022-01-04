/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.timewheel;

import lombok.Data;

/**
 * <p>
 * 要执行的任务
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/22 10:37 AM
 */
@Data
public abstract class TimerTask implements Runnable {

    /**
     * 表示当前任务延迟多久后执行(单位ms)，比如说延迟3s，则此值为3000
     */
    public long delayMs;

    /**
     * 设置当前任务过期时间（单位ms）
     */
    public TimerTask(long delayMs) {
        this.delayMs = delayMs;
    }

    /**
     * 指向TimerTaskEntry对象，一个TimerTaskEntry包含一个TimerTask，TimerTaskEntry是可复用的
     */
    private TimerTaskEntry timerTaskEntry = null;

    /**
     * 取消当前任务，就是从TimerTaskEntry移出TimerTask，并且把当前的timerTaskEntry置空
     */
    public synchronized void cancel() {
        if(timerTaskEntry != null) {
            timerTaskEntry.remove();
        }
        timerTaskEntry = null;
    }

    /**
     * 设置当前任务绑定的TimerTaskEntry
     */
    public synchronized void setTimerTaskEntry(TimerTaskEntry entry) {
        if(timerTaskEntry != null && timerTaskEntry != entry) {
            timerTaskEntry.remove();
        }
        timerTaskEntry = entry;
    }

    /**
     * 获取当前任务绑定的TimerTaskEntry
     */
    public TimerTaskEntry getTimerTaskEntry() {
        return timerTaskEntry;
    }
}
