package com.example.dllo.phonebook;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by dllo on 16/5/9.
 */
public class MyThreadPool {

    private static MyThreadPool myThreadPool;
    private ThreadPoolExecutor threadPoolExecutor;

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    private MyThreadPool(){
        int CPUCores = Runtime.getRuntime().availableProcessors();
        threadPoolExecutor = new ThreadPoolExecutor
                (CPUCores + 1,2*CPUCores +1,60L, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>());
    }

    public static MyThreadPool getInstance(){
        if (myThreadPool == null){
            synchronized (MyThreadPool.class){
                if (myThreadPool == null){
                    myThreadPool = new MyThreadPool();
                }
            }
        }
        return myThreadPool;
    }

}
