package com.github.thushear.guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.junit.Test;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by kongming on 2017/5/11.
 */
public class GuavaCacheEvictionTest {


    Cache writeExpirelocalCache = CacheBuilder.newBuilder().concurrencyLevel(8)
            .expireAfterWrite(3, TimeUnit.SECONDS)
//                .expireAfterAccess(5, TimeUnit.SECONDS)
            .maximumSize(100)
            .softValues()
            .removalListener(new RemovalListener() {
                @Override
                public void onRemoval(RemovalNotification removalNotification) {


//                    System.out.println(String.format("%s was removed,value is %s , cause is %s",removalNotification.getKey(),removalNotification.getValue(),removalNotification.getCause()));

                }
            }).build();



    Cache accesesExpirelocalCache = CacheBuilder.newBuilder().concurrencyLevel(8)
//                .expireAfterWrite(5, TimeUnit.SECONDS)
            .expireAfterAccess(3, TimeUnit.SECONDS)
            .maximumSize(100)
            .softValues()
            .removalListener(new RemovalListener() {
                @Override
                public void onRemoval(RemovalNotification removalNotification) {


//                    System.out.println(String.format("%s was removed,value is %s , cause is %s",removalNotification.getKey(),removalNotification.getValue(),removalNotification.getCause()));

                }
            }).build();


    int iterateNum = 1000;




    @Test
    public void hotRateTest() throws InterruptedException, ExecutionException {
        Random random = new Random();

        for (int i = 0; i < 40; i++) {
            oneWriteAndGet(random);
        }

        hotRateCalc(writeExpirelocalCache,"writeExpirelocalCache");
        hotRateCalc(accesesExpirelocalCache,"accesesExpirelocalCache");
    }


    private void hotRateCalc(Cache cache, String desc){

        int hotNum = 0;

        Set<Map.Entry> entrySet = cache.asMap().entrySet();
        int totalNum = entrySet.size();
        for (Map.Entry entry : entrySet) {
            int key = Integer.valueOf(String.valueOf(entry.getKey()));
            if ( key < 100){
                hotNum ++;
            }
        }

        System.err.println(desc + " stats:");
        System.err.println("hotNum:" + hotNum);
        System.err.println("totalNum:" + totalNum);
        System.err.println( "hot rate :" + Double.valueOf(hotNum)/ Double.valueOf(totalNum) );
        System.err.println("cacheStats: evictionCount " + cache.stats().evictionCount() + " |hitCount " + cache.stats().hitCount() + "| hitRate " + cache.stats().hitRate()
                + "| loadSuccessCount " + cache.stats().loadSuccessCount() + " |missCount  " + cache.stats().missCount() + " |missRate " + cache.stats().missRate());
    }



    private void oneHotWriteAndGet() throws ExecutionException {
        for (int i = 0; i < 100; i++) {
            final  int hot = i;
            writeExpirelocalCache.get(hot, new Callable() {
                @Override
                public Object call() throws Exception {
                    return hot;
                }
            });
            accesesExpirelocalCache.get(hot, new Callable() {
                @Override
                public Object call() throws Exception {
                    return hot;
                }
            });
        }
    }


    /**
     * 模拟一次随机读写
     * @param random
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private void oneWriteAndGet(Random random) throws InterruptedException, ExecutionException {
        TimeUnit.SECONDS.sleep(1);

        for (int i = 0; i < iterateNum; i++) {
            final int rand = random.nextInt(1000);
            writeExpirelocalCache.get(rand, new Callable() {
                @Override
                public Object call() throws Exception {
                    return rand;
                }
            });
            accesesExpirelocalCache.get(rand, new Callable() {
                @Override
                public Object call() throws Exception {
                    return rand;
                }
            });


            final int randHot = random.nextInt(100);
            writeExpirelocalCache.get(randHot, new Callable() {
                @Override
                public Object call() throws Exception {
                    return randHot;
                }
            });
            accesesExpirelocalCache.get(randHot, new Callable() {
                @Override
                public Object call() throws Exception {
                    return randHot;
                }
            });


        }
//        oneHotWriteAndGet();
    }


    @Test
    public void expireAfterWriteTest() throws InterruptedException {
        Cache localCache = CacheBuilder.newBuilder().concurrencyLevel(8)
                .expireAfterWrite(5, TimeUnit.SECONDS)
//                .expireAfterAccess(5, TimeUnit.SECONDS)
                .maximumSize(100)
                .softValues()
                .removalListener(new RemovalListener() {
                    @Override
                    public void onRemoval(RemovalNotification removalNotification) {


                        System.out.println(String.format("%s was removed,value is %s , cause is %s",removalNotification.getKey(),removalNotification.getValue(),removalNotification.getCause()));

                    }
                }).build();
        for (int i = 0; i < 100; i++) {
            localCache.put(i,i);
        }

        TimeUnit.SECONDS.sleep(3);

        for (int i = 0; i < 50; i++) {
            localCache.getIfPresent(i);
        }

        TimeUnit.SECONDS.sleep(3);

        for (int i = 100; i < 200; i++) {
            localCache.put(i,i);
        }
//        for (int i = 0; i < 100; i++) {
//            localCache.getIfPresent(i);
//        }
//        localCache.put(101,101);
//        localCache.cleanUp();
        synchronized (GuavaCacheEvictionTest.class){
            GuavaCacheEvictionTest.class.wait();
        }


    }




    @Test
    public void expireAfterAccessTest() throws InterruptedException {
        Cache localCache = CacheBuilder.newBuilder().concurrencyLevel(8)
                .expireAfterWrite(15, TimeUnit.SECONDS)
                .expireAfterAccess(5, TimeUnit.SECONDS)
                .maximumSize(100)
                .softValues()
                .removalListener(new RemovalListener() {
                    @Override
                    public void onRemoval(RemovalNotification removalNotification) {


                        System.out.println(String.format("%s was removed,value is %s , cause is %s",removalNotification.getKey(),removalNotification.getValue(),removalNotification.getCause()));

                    }
                }).build();

        for (int i = 0; i < 100; i++) {
            localCache.put(i,i);
        }


        for (int j = 0; j < 10; j++) {



            TimeUnit.SECONDS.sleep(3);

            for (int i = 0; i < 50; i++) {
                localCache.getIfPresent(i);
            }

            TimeUnit.SECONDS.sleep(3);

            localCache.cleanUp();


            for (int i = 50; i < 100; i++) {
                localCache.put(i,i);
            }



        }



//        localCache.put(101,101);

        synchronized (GuavaCacheEvictionTest.class){
            GuavaCacheEvictionTest.class.wait();
        }


    }


}
