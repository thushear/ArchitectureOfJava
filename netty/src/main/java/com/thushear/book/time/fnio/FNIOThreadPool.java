package com.thushear.book.time.fnio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FNIOThreadPool {


    private ExecutorService executorService;

    public FNIOThreadPool(int maxPoolSize , int queueSize) {

        executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),maxPoolSize,120l, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(queueSize));

    }


    public void execute(Runnable runnable){
        executorService.submit(runnable);
    }
}
