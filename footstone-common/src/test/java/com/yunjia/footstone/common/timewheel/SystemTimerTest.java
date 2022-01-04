/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.common.timewheel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * TODO 类注释
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/22 2:39 PM
 */
public class SystemTimerTest {

    //驱动时间轮向前的线程
    public static  SystemTimer timer = new SystemTimer(1000L,5);


    public static void runTask() throws Exception {
        for(int i = 0;i < 10;i++) {
            System.out.println("添加任务==" + i);
            // 添加任务，每个任务间隔1s
            long delayMs = i * 1000L;
            timer.add(new TimerTask(delayMs) {
                @Override
                public void run() {
                    System.out.println("运行testTask【" + this.getDelayMs() + "】的时间: " + System.currentTimeMillis());
                }
            });
        }
    }

    public static void main(String[] args) throws Exception {
        runTask();
        Thread.sleep(15000);
        System.out.println("===关闭Timer开始");
        timer.shutdown();
        System.out.println("===关闭Timer结束");
    }
}
