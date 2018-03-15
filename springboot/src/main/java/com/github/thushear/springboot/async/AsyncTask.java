package com.github.thushear.springboot.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * <pre>

 * Created: 2018年03月12日 下午 14:57
 * Version: 1.0
 * Project Name: architecture
 * Last Edit Time: 2018年03月12日 下午 14:57
 * Update Log:
 * Comment:
 * </pre>
 */
@Component
public class AsyncTask {


    @Async
    public Future<String> doTaskOne() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread.sleep(2000);
        System.err.println("taskone cost : " + (System.currentTimeMillis() - start) + " ms");
        return new AsyncResult<>("task one");
    }

    @Async
    public Future<String> doTaskTwo() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread.sleep(3000);
        System.err.println("taskone cost : " + (System.currentTimeMillis() - start) + " ms");
        return new AsyncResult<>("task two");
    }


    @Async
    public Future<String> doTaskThree() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread.sleep(5000);
        System.err.println("taskone cost : " + (System.currentTimeMillis() - start) + " ms");
        return new AsyncResult<>("task three");
    }


}
