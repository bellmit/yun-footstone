/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.timewheel;

import cn.hutool.core.thread.ThreadUtil;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 时间轮接口实现
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/22 1:44 PM
 */
@Slf4j
public class SystemTimer implements Timer {

    /**
     * CPU核心数 线程池最大核心线程数建议CPU核心数*2
     */
    private static final int nThreads = Runtime.getRuntime().availableProcessors();

    private static final int CORE_POOL_SIZE = nThreads * 2;
    private static final int MAXIMUM_POOL_SIZE = nThreads * 10;
    private static final int MAXIMUM_QUEUE_SIZE = 1024;

    /**
     * 底层时间轮每个槽位时间跨度（1ms）
     */
    private Long tickMs = 1L;

    /**
     * 时间轮大小（槽位数）
     */
    private Integer wheelSize = 20;

    /**
     * 启动时间
     */
    private Long startMs = System.currentTimeMillis();

    /**
     * 用来执行TimerTask任务
     */
    private final ExecutorService taskExecutor;

    /**
     * 轮询delayQueue获取过期任务线程
     */
    private final ExecutorService bossExecutor = ThreadUtil.newSingleExecutor();

    /**
     * 延迟队列
     */
    private DelayQueue<TimerTaskList> delayQueue = new DelayQueue<>();

    /**
     * 任务数
     */
    private AtomicInteger taskCounter = new AtomicInteger(0);

    /**
     * 底层时间轮
     */
    private TimingWheel timingWheel;

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    /**
     * 用来执行时间轮的重新排列，及上一个槽中的任务列表被执行后，后面的槽中的任务列表移动
     */
    private Consumer<TimerTaskEntry> reinsert = (timerTaskEntry) -> addTimerTaskEntry(timerTaskEntry);

    public SystemTimer(Long tickMs, Integer wheelSize) {
        this(tickMs, wheelSize, ThreadUtil.newExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, MAXIMUM_QUEUE_SIZE));
    }

    public SystemTimer(Long tickMs, Integer wheelSize, ExecutorService taskExecutor) {
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        this.startMs = System.currentTimeMillis();
        this.timingWheel = new TimingWheel(
                tickMs,
                wheelSize,
                startMs,
                taskCounter,
                delayQueue
        );
        if(null != taskExecutor) {
            this.taskExecutor = taskExecutor;
        } else {
            this.taskExecutor = ThreadUtil.newExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, MAXIMUM_QUEUE_SIZE);
        }
        this.bossExecutor.submit(() -> {
            while(!bossExecutor.isShutdown()) {
                try {
                    this.advanceClock(tickMs);
                } catch(Exception e) {
                    log.error("advanceClock error", e);
                }
            }
        });
    }

    /**
     * 添加一个新任务
     * 可能会多个线程操作，所以需要加锁
     */
    @Override
    public void add(TimerTask timerTask) {
        readLock.lock();
        try{
            addTimerTaskEntry(new TimerTaskEntry(timerTask,timerTask.delayMs + System.currentTimeMillis()));
        }finally {
            readLock.unlock();
        }
    }

    /**
     * 往时间轮添加任务
     * @param timerTaskEntry
     */
    private void addTimerTaskEntry(TimerTaskEntry timerTaskEntry) {
        if(!timingWheel.add(timerTaskEntry)) {
            // 返回false并且任务未取消，则提交当前任务立即执行。
            if(!timerTaskEntry.cancel()) {
                taskExecutor.submit(timerTaskEntry.timerTask);
            }
        }
    }

    /**
     * 驱动时间
     */
    @Override
    public boolean advanceClock(Long timeoutMs) throws Exception {
        // 使用阻塞队列获取任务
        TimerTaskList bucket = delayQueue.poll(timeoutMs, TimeUnit.MILLISECONDS);
        if(bucket != null) {
            writeLock.lock();
            try{
                while(bucket != null) {
                    timingWheel.advanceClock(bucket.getExpiration());
                    // 驱动时间后，需要移动TimerTaskList到上一个槽或者从上一层移动到本层
                    bucket.flush(reinsert);
                    bucket = delayQueue.poll();
                }
            }finally {
                writeLock.unlock();
            }
            return true;
        }else {
            return false;
        }
    }

    /**
     * 等待执行的任务数
     */
    @Override
    public int size() {
        return taskCounter.get();
    }

    /**
     * 停止时间轮，未执行的任务将无法被执行
     */
    @Override
    public void shutdown() {
        bossExecutor.shutdown();
        taskExecutor.shutdown();
    }
}
