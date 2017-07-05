package com.transfar.smarttda.tool;

import com.transfar.smarttda.configuration.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by wulei
 * Data: 2016/11/8.
 *
 * 统一线程池
 */

public class ThreadFactory {
    // 获取CPU核心数
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    // 临时任务线程池
    private static final ExecutorService FIX_EXECUTOR =  Executors.newSingleThreadExecutor();
    // 定时任务线程池,执行周期性任务
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR =  Executors.newSingleThreadScheduledExecutor();

    /**
     * 执行一个临时任务
     */
    public static void executor(Runnable task){
        FIX_EXECUTOR.submit(task);
    }

    /**
     * 执行一个定时任务
     * @param task
     * @param period
     */
    public static ScheduledFuture executorTimer(Runnable task, long period){
        return SCHEDULED_EXECUTOR.scheduleAtFixedRate(task, Configuration.SDK_START_DELAY, period, TimeUnit.MILLISECONDS);
    }
}

