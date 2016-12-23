package org.thushear.tut.concurrent;

import java.util.concurrent.*;

/**
 * Created by kongming on 2016/3/15.
 */
public class Executors2 {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test1();
    }


    public static void test1() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Callable callable = () -> {

            TimeUnit.SECONDS.sleep(1);
            return 123;
        };

        Future<Integer> future = executorService.submit(callable);
        System.out.println(future.isDone());
        System.out.println(future.get());
        System.out.println(future.isDone());

    }
}
