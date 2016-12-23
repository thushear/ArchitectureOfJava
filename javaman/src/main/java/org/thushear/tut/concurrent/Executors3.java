package org.thushear.tut.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.Callable;
/**
 * Created by kongming on 2016/3/15.
 */
public class Executors3 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        testTimeOut();
//        testInvokeAll();

//        invokeAny();

//        testSchedule();

        testScheduleAtFixedRate();
//        testScheduleWithFixedDelay();
    }


    public static void testScheduleWithFixedDelay(){
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {

            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("scheduling:" + System.nanoTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        executorService.scheduleWithFixedDelay(task,0,1,TimeUnit.SECONDS);
    }

    public static void testScheduleAtFixedRate(){

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("scheduling : " + System.nanoTime());
        };

        int initalDelay = 0;
        int period = 1;
        executorService.scheduleAtFixedRate(task,initalDelay,period,TimeUnit.SECONDS);
    }


    public static void testSchedule() throws InterruptedException {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("scheduling : " + System.nanoTime());

        ScheduledFuture<?> future = executorService.schedule(task,3,TimeUnit.SECONDS);

        TimeUnit.MILLISECONDS.sleep(1337);

        long remaining = future.getDelay(TimeUnit.MILLISECONDS);
        System.out.println(remaining);
    }



    public static void invokeAny() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(callable("task1",3),callable("task2",2),callable("task3",3));

        String result = executorService.invokeAny(callables);
        System.out.println(result);

    }

    private static Callable<String> callable(String result, long sleepSeconds) {
        return () -> {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return result;
        };
    }

    public static void testInvokeAll() throws InterruptedException {

        ExecutorService executorService = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList( () ->"task1", () ->"task2", () ->"task3" );

        executorService.invokeAll(callables)
                .stream().map(stringFuture -> {
            try {
                System.out.println(Thread.currentThread().getName());
                return stringFuture.get();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }).forEach(System.out::println);
    }


    //java.util.concurrent.TimeoutException
    public static void testTimeOut(){

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future<Integer> future = executorService.submit(() -> {
            TimeUnit.SECONDS.sleep(2);
            return 123;
        } );

        try {
            future.get(1,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
