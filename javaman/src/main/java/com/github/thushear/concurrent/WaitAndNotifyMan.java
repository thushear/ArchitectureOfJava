package com.github.thushear.concurrent;

import com.google.common.collect.Queues;
import org.apache.commons.lang.math.RandomUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 正确的使用wait 和 notify notifyall
 *  生产消费 等待通知俩种模式
 *
 * Created by kongming on 2016/11/10.
 */
public class WaitAndNotifyMan {

    static final int SIZE = 3;

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue blockingQueue = Queues.newLinkedBlockingQueue();
//        new Producer(blockingQueue).start();
//        new Consumer(blockingQueue).start();

        new Thread(new Wait(),"wait-thread").start();

        TimeUnit.SECONDS.sleep(2);

        new Thread(new Notify(),"notify-thread").start();
    }


    static class  Consumer extends Thread{

        BlockingQueue blockingQueue ;

        public Consumer(BlockingQueue blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            while (true){

                synchronized (blockingQueue){

                    while (blockingQueue.size() == 0){
                        try {
                            blockingQueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(blockingQueue.remove());
                    blockingQueue.notifyAll();
                }
            }
        }
    }


   static   class   Producer extends Thread{

        BlockingQueue blockingQueue ;

        public Producer(BlockingQueue blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            while (true){

                synchronized (blockingQueue){
                    while (blockingQueue.size() == SIZE){
                        try {
                            blockingQueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    blockingQueue.add(RandomUtils.nextInt());
                    blockingQueue.notifyAll();
                }

            }
        }
    }

    static Object lock = new Object();

    static volatile boolean flag = true;



    static class Wait implements Runnable {

        @Override
        public void run() {
            //获取锁 拥有锁的Monitor
            synchronized (lock){
                //当条件不满足时 继续wait 同时释放了lock的锁
                while (flag){
                    System.out.println("flag = true need wait @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("flag = false get here @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }


    static class Notify implements  Runnable {


        @Override
        public void run() {
            //枷锁 拥有lock的monitor
            synchronized (lock){
                //获取lock的锁 然后进行通知 通知时不会释放lock的锁 直到当前线程释放了 lock后 WaitThread 才从wait方法中返回
                lock.notify();
                System.out.println("hold lock @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                try {
                    TimeUnit.SECONDS.sleep(5);
                    flag = false;
                    System.out.println("hold lock @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
