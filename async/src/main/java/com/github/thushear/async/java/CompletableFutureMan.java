package com.github.thushear.async.java;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kongming on 2016/11/11.
 */
public class CompletableFutureMan {

    static final AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(()->{
            return atomicInteger.incrementAndGet();
        });
        System.out.println(completableFuture.isDone());
        System.out.println(completableFuture.get());

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(CompletableFutureMan::getMoreData);
        Future<Integer> f = future.whenComplete((t, u)->{
            System.out.println(t);
            System.out.println(u);
        });
        System.out.println(f.get());



    }


    static int getMoreData(){
        System.out.println("begin to start compute");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("begin to end compute");
        return new Random().nextInt();
    }

}
