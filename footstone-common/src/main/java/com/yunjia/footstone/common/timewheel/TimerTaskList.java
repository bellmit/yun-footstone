/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.timewheel;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import lombok.Data;

/**
 * <p>
 * 每个槽中的任务列表
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/22 10:57 AM
 */
@Data
public class TimerTaskList implements Delayed {

    /**
     * 当前列表中包含的任务数
     */
    private AtomicInteger taskCounter;

    /**
     * 列表的头结点
     */
    private TimerTaskEntry root;

    /**
     * 过期时间
     */
    private AtomicLong expiration = new AtomicLong(-1L);


    public TimerTaskList(AtomicInteger taskCounter) {
        this.taskCounter = taskCounter;
        this.root =  new TimerTaskEntry(null,-1L);
        root.setNext(root);
        root.setPrev(root);
    }

    /**
     * 给当前槽设置过期时间
     * @param expirationMs
     * @return
     */
    public boolean setExpiration(Long expirationMs) {
        return expiration.getAndSet(expirationMs) != expirationMs;
    }

    public Long getExpiration() {
        return expiration.get();
    }

    /**
     * 用于遍历当前列表中的任务
     * @param f
     */
    public synchronized  void foreach(Consumer<TimerTask> f) {
        TimerTaskEntry entry = root.getNext();
        while(entry != root) {
            TimerTaskEntry nextEntry = entry.getNext();
            if(!entry.cancel()) {
                f.accept(entry.timerTask);
            }
            entry = nextEntry;
        }
    }

    /**
     * 添加任务到列表中
     * @param timerTaskEntry
     */
    public void add(TimerTaskEntry timerTaskEntry) {
        boolean done = false;
        while(!done) {
            // 如果TimerTaskEntry已经在别的list中就先移除，同步代码块外面移除，避免死锁，一直到成功为止
            timerTaskEntry.remove();
            synchronized (this) {
                synchronized (timerTaskEntry) {
                    if(timerTaskEntry.getList() == null) {
                        TimerTaskEntry tail = root.getPrev();
                        timerTaskEntry.setNext(root);
                        timerTaskEntry.setPrev(tail);
                        timerTaskEntry.setList(this);
                        tail.setNext(timerTaskEntry);
                        root.setPrev(timerTaskEntry);
                        taskCounter.incrementAndGet();
                        done = true;
                    }
                }
            }
        }
    }

    /**
     * 移出任务
     * @param timerTaskEntry
     */
    public synchronized void remove(TimerTaskEntry timerTaskEntry) {
        synchronized (timerTaskEntry) {
            if(timerTaskEntry.getList() == this) {
                timerTaskEntry.getNext().setPrev(timerTaskEntry.getPrev());
                timerTaskEntry.getPrev().setNext(timerTaskEntry.getNext());
                timerTaskEntry.setNext(null);
                timerTaskEntry.setPrev(null);
                timerTaskEntry.setList(null);
                taskCounter.decrementAndGet();
            }
        }
    }

    /**
     * 驱动时间后，需要移动TimerTaskList到上一个槽或者从上一层移动到本层
     * @param f
     */
    public synchronized void flush(Consumer<TimerTaskEntry> f) {
        TimerTaskEntry head = root.getNext();
        while(head != root) {
            remove(head);
            f.accept(head);
            head = root.getNext();
        }
        expiration.set(-1L);
    }

    /**
     * 获得当前任务剩余时间
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(Math.max(getExpiration() - System.currentTimeMillis(),0),TimeUnit.MICROSECONDS);
    }

    @Override
    public int compareTo(Delayed d) {
        TimerTaskList other = (TimerTaskList) d;
        return Long.compare(getExpiration(),other.getExpiration());
    }
}
