/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.timewheel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 时间轮
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/22 11:22 AM
 */
public class TimingWheel {

    /**
     * 每一个槽表示的时间范围
     */
    private Long tickMs;

    /**
     * 时间轮大小，即每一层时间轮的大小(槽的个数)
     */
    private Integer wheelSize;

    /**
     * 系统的启动时间
     */
    private Long startMs;

    /**
     * 当前层任务数
     */
    private AtomicInteger taskCounter;

    /**
     * 延迟队列，用于从队列取每个任务列表
     */
    private DelayQueue<TimerTaskList> queue;

    /**
     * 每一层时间轮代表的时间跨度
     */
    private Long interval;

    /**
     * 每一层的每一个槽中的时间任务列表
     */
    private List<TimerTaskList> buckets;

    /**
     * 修正后的系统启动时间
     */
    private Long currentTime;

    /**
     * 上一层时间轮
     */
    private TimingWheel overflowWheel = null;

    public TimingWheel(Long tickMs, Integer wheelSize, Long startMs, AtomicInteger taskCounter, DelayQueue<TimerTaskList> queue) {
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        this.startMs = startMs;
        this.taskCounter = taskCounter;
        this.queue = queue;
        interval = tickMs * wheelSize;
        // 当前时间，往前推
        currentTime = startMs - (startMs % tickMs);
        buckets = new ArrayList<>(wheelSize);
        for(int i = 0;i < wheelSize;i++) {
            // 创建每一个槽中的列表
            buckets.add(new TimerTaskList(taskCounter));
        }
    }

    /**
     * 创建上层时间轮
     */
    public synchronized void addOverflowWheel() {
        if(overflowWheel == null) {
            overflowWheel = new TimingWheel(
                    // 此处interval即表示上一层时间轮每个槽位表示的范围
                    interval,
                    wheelSize,
                    currentTime,
                    taskCounter,
                    queue
            );
        }
    }

    /**
     * 添加任务
     * @param timerTaskEntry
     * @return
     */
    public boolean add(TimerTaskEntry timerTaskEntry) {
        Long expiration = timerTaskEntry.expirationMs;
        // 任务是否已经取消，取消则返回
        if(timerTaskEntry.cancel()) {
            return false;
            // 当前任务是否已经过期，如果过期则返回false，要立即执行
        }else if(expiration < currentTime + tickMs) {
            return false;
            // 判断当前任务能否在添加到当前时间轮
        }else if(expiration < currentTime + interval) {
            Long virtualId = expiration / tickMs;
            // 计算当前任务要分配在哪个槽中
            TimerTaskList bucket = buckets.get((int)(virtualId % wheelSize));
            bucket.add(timerTaskEntry);
            //更新槽的过期时间，添加入延迟队列
            if(bucket.setExpiration(virtualId * tickMs)) {
                queue.offer(bucket);
            }
            return true;
        }else {
            //添加任务到高层时间轮
            if(overflowWheel == null) {
                addOverflowWheel();
            }
            return overflowWheel.add(timerTaskEntry);
        }
    }

    /**
     * 向前驱动时间
     * @param timeMs
     */
    public void advanceClock(Long timeMs) {
        if(timeMs >= currentTime + tickMs) {
            currentTime = timeMs - (timeMs % tickMs);

            if(overflowWheel != null) {
                overflowWheel.advanceClock(currentTime);
            }
        }
    }
}
