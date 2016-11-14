package com.github.thushear.async.java;

import java.util.concurrent.*;

/**
 * Created by kongming on 2016/11/11.
 */

public class FutureMan {


    private static final ExecutorService threadPool = Executors.newFixedThreadPool(3);


    /**
     * Java program to show how to use Future in Java. Future allows to write * asynchronous code in Java,
     * where Future promises result to be available in * future * * @author Javin
     * Java Future 就像Promise 保证会在未来的某个时间段获取到一个结果
     */

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        FactorialCalculator factorialCalculator = new FactorialCalculator(10);

        System.out.println("submitting task...");

        Future future = threadPool.submit(factorialCalculator);

        while (!future.isDone()){
            System.out.println("task is not done");
            TimeUnit.MILLISECONDS.sleep(100);
        }

        System.out.println("task is done ");
        System.out.println("task result :" + future.get());
        threadPool.shutdown();

    }

    static class FactorialCalculator implements Callable {

        private final int number;

        public FactorialCalculator(int number) {
            this.number = number;
        }

        @Override
        public Long call() throws Exception {
            return factorial(number);
        }

        private long factorial(int number) throws InterruptedException {
            if (number < 0) {
                throw new IllegalArgumentException("Number must be greater than zero");
            }
            long result = 1;
            while (number > 0) {
                Thread.sleep(1); // adding delay for example
                result = result * number;
                number--;
            }
            return result;
        }


    }


}
