package org.thushear.tut.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * Created by kongming on 2016/3/15.
 */
public class Thread1 {


    public static void main(String[] args) {

//        test1();

//        System.out.println(1/4);
        test2();
    }


    static void test2(){

        Runnable runnable = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("foo :" + threadName);
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("bar :" + threadName);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        runnable.run();
        Thread thread = new Thread(runnable);
        thread.start();

    }

    static void test1(){

        Runnable runnable = () -> {

            System.out.println(" thread name :"  + Thread.currentThread().getName());

        };
        Thread thread  = new Thread(runnable);

        thread.start();

        runnable.run();



        System.out.println("done");
    }
}
