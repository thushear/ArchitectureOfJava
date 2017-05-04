package com.github.thushear.guava.cache;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.*;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by kongming on 2017/5/4.
 */
public class GuavaCacheCase {


    static Map<String,String> originalData ;

    static {
        originalData = new HashMap<>();
        originalData.put("1","1");
        originalData.put("2","2");
        originalData.put("3","3");
        originalData.put("4","4");
        originalData.put("5","5");
    }


    public static void main(String[] args) {

        LoadingCache<String,String> localCache = CacheBuilder.newBuilder().maximumSize(20)
                .recordStats().refreshAfterWrite(3,TimeUnit.SECONDS).removalListener(new RemovalListener<String, String>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, String> notification) {
                        System.out.println("remove notification " + notification.getKey() + ":" + notification.getValue() + "||" + notification.getCause());
                    }
                })
                .expireAfterWrite(10, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {

                        return originalData.get(key);
                    }

                    @Override
                    public Map<String, String> loadAll(Iterable<? extends String> keys) throws Exception {


                        return originalData;
                    }
                });

        List allKeys = Lists.newArrayList("1","2","3","4","5");
//        {
//            allKeys.add("1");allKeys.add("2");allKeys.add("3");allKeys.add("4");allKeys.add("5");
//        }
        try {
            localCache.getAll(allKeys);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            String value = localCache.get("1");
            System.out.println("value = " + value);

        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        try {
            String value2 = localCache.get("2", new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return originalData.get("2");
                }
            });
            System.out.println("value2 = " + value2);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        localCache.put("1","11");

        for (int i = 0; i < 100; i++) {
            localCache.put("" + i, i + "");
        }
        try {
            TimeUnit.SECONDS.sleep(11);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 100; i < 200; i++) {
            localCache.put("" + i, i + "");
        }
        ConcurrentMap<String,String> concurrentMap = localCache.asMap();
        System.out.println("concurrentMap = " + concurrentMap);

        try {
            System.out.println(localCache.get("1"));
            System.out.println(localCache.get("2"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        CacheStats cacheStats = localCache.stats();
        System.out.println(cacheStats.hitCount() + " == " + cacheStats.hitRate() + "==" + cacheStats.evictionCount() + "==" + cacheStats.averageLoadPenalty());
        System.out.println(JSON.toJSONString(cacheStats));
        System.out.println(cacheStats);

    }
}
