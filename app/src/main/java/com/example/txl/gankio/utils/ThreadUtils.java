package com.example.txl.gankio.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/25
 * description：线程池的配置
 */
public class ThreadUtils {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private static final int CORE_POOL_SIZE= CPU_COUNT +1;

    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2+1;

    private static final long KEEP_ALIVE = 10L;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread( r, "ThreadUtils#"+mCount.getAndIncrement() );
        }
    };

    private static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor( CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_ALIVE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(  ), sThreadFactory );

    public static void execute(Runnable runnable){
        THREAD_POOL_EXECUTOR.execute( runnable );
    }
}
