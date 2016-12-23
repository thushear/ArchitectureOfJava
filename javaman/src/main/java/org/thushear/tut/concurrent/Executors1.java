package org.thushear.tut.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by kongming on 2016/3/15.
 */
public class Executors1 {


    public static void main(String[] args) {

        test1(2);

    }

    public static void test1(long seconds){

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(seconds);
                String threadName = Thread.currentThread().getName();
                System.out.println("current thread :" + threadName);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        stop(executorService);
    }


    public static void stop(ExecutorService executorService){

        executorService.shutdown();
        try {
            executorService.awaitTermination(5,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (!executorService.isTerminated()){
                System.out.println("kill non killed thread");
                executorService.shutdownNow();
            }
        }

    }

}
