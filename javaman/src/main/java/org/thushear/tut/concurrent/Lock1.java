package org.thushear.tut.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * Created by kongming on 2016/3/16.
 */
public class Lock1 {


    public static void main(String[] args) {

//        new Lock1().testThreadUnsafe();
//        new Lock1().testThreadsafe();

        new Lock1().testReentrantLockThreadsafe();
    }

    int count = 0;

    void  increment(){
        count = count + 1;
    }

    void safeIncrement(){
        synchronized (this){
            count = count + 1;
        }
    }

    ReentrantLock lock = new ReentrantLock();


    public void testReentrantLockIncr(){

        lock.lock();

        try {
            count = count + 1;
        } finally {
            lock.unlock();
        }

    }


    public  void testReentrantLockThreadsafe(){

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        IntStream.range(0,10000)
                .forEach( value -> executorService.submit( this::testReentrantLockIncr ) );

        ConcurrentUtils.stop(executorService);
        System.out.println(count);

    }


    public  void testThreadsafe(){

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        IntStream.range(0,10000)
                .forEach( value -> executorService.submit( this::safeIncrement ) );

        ConcurrentUtils.stop(executorService);
        System.out.println(count);

    }


    /**
     * 读改写线程不安全实例
     *
     */
    public  void testThreadUnsafe(){

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        IntStream.range(0,10000)
                .forEach( value -> executorService.submit( this::increment ) );

        ConcurrentUtils.stop(executorService);
        System.out.println(count);

    }
}
