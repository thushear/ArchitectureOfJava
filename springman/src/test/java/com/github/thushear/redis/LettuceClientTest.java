package com.github.thushear.redis;

import com.github.thushear.BaseTest;
import com.google.common.collect.Lists;
import com.lambdaworks.redis.*;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.async.RedisAsyncCommands;
import com.lambdaworks.redis.api.rx.RedisStringReactiveCommands;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.lambdaworks.redis.cluster.RedisClusterClient;
import com.lambdaworks.redis.cluster.api.StatefulRedisClusterConnection;
import com.lambdaworks.redis.cluster.api.async.AsyncExecutions;
import com.lambdaworks.redis.cluster.api.async.AsyncNodeSelection;
import com.lambdaworks.redis.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import com.lambdaworks.redis.codec.Utf8StringCodec;
import com.lambdaworks.redis.masterslave.MasterSlave;
import com.lambdaworks.redis.masterslave.StatefulRedisMasterSlaveConnection;
import org.junit.Test;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by kongming on 2017/4/18.
 */
public class LettuceClientTest extends BaseTest {


    @Test
    public void testMockData(){
        RedisClient redisClient = RedisClient.create(RedisURI.Builder.redis("192.168.56.103",6383).build());
        RedisAsyncCommands<String,String> asyncCommands = redisClient.connect().async();
        List<RedisFuture> futures = Lists.newArrayList();
        for (int i = 0; i < 1000; i++) {
            RedisFuture<String> future = asyncCommands.set("foo" + i, "bar" + i);
            futures.add(future);
        }

        LettuceFutures.awaitAll(1,TimeUnit.SECONDS,futures.toArray(new RedisFuture[futures.size()]));

    }


    @Test
    public void testBasicBIO(){
        RedisClient redisClient = RedisClient.create("redis://192.168.159.130:6379/0");
        redisClient.setDefaultTimeout(2, TimeUnit.SECONDS);
        StatefulRedisConnection<String,String> connection = redisClient.connect();
        RedisCommands<String,String> syncStringCommands = connection.sync();
        syncStringCommands.set("key","hello lettuce");

        connection.close();
        redisClient.shutdown();

    }



    @Test
    public void testAsyncNIO() throws ExecutionException, InterruptedException {
        final CompletableFuture<String> future = new CompletableFuture<>();
        future.thenRun(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("thread got value :" + future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        future.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String value) {
                System.out.println("consumer got value : " + value);
            }
        });

        System.out.println("current state:" + future.isDone());
        future.complete("complete");
        System.out.println("current state:" + future.isDone());
        System.out.println("got value:" + future.get());
    }


    @Test
    public void testAsyncCommands() throws ExecutionException, InterruptedException, TimeoutException {
        RedisClient redisClient = RedisClient.create(RedisURI.Builder.redis("192.168.56.103",6383).build());
        RedisAsyncCommands<String,String> asyncCommands = redisClient.connect().async();
        RedisFuture<String> future = asyncCommands.get("foo1");
        future.thenApply(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        })
                .thenAccept(System.out::println);
        //
//        String value = future.get();
        // better to use timeout setting
        String value = future.get(100L,TimeUnit.MILLISECONDS);
        System.out.println("value = " + value);

        RedisFuture<String> set = asyncCommands.set("key","value");
        RedisFuture<String> get = asyncCommands.get("key");
        System.out.println("set = " + set.get());
        System.out.println("get.get() = " + get.get());

    }


    @Test
    public void testObserver(){
        Observable.just("ben","mi","app").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("hello " + s + " !");
            }

        });


        Observable.just("a","b","c").subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onerror:" + e);
            }

            @Override
            public void onNext(String s) {
                System.out.println("hello " + s + "!");
            }
        });


    }



    @Test
    public void testReactive(){
        RedisClient redisClient = RedisClient.create(RedisURI.Builder.redis("192.168.56.103",6383).build());
        RedisStringReactiveCommands<String,String> redisStringReactiveCommands = redisClient.connect().reactive();
        redisStringReactiveCommands.get("key").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("call " + s);
            }
        });
        redisStringReactiveCommands.get("key").subscribe(value -> System.out.println(value));

        Observable.just("key","foo1","foo2").flatMap(redisStringReactiveCommands::get).subscribe(value -> System.out.println(value));

    }


    @Test
    public void testMasterSlave() throws ExecutionException, InterruptedException {
        RedisClient redisClient = RedisClient.create();
        StatefulRedisMasterSlaveConnection connection = MasterSlave.connect(redisClient,new Utf8StringCodec(),RedisURI.Builder.redis("192.168.56.102",6379).build());
        connection.setReadFrom(ReadFrom.SLAVE);
        RedisAsyncCommands<String,String> asyncCommands = connection.async();
        RedisFuture<List<String>>  keys = asyncCommands.keys("*");

        System.err.println(keys.get());
        RedisFuture<String> clusterInfo = asyncCommands.clusterInfo();
        System.err.println("===== cluster = " + clusterInfo.get());
        System.err.println("asyncCommands = " + asyncCommands.clusterNodes());



        connection.close();
        redisClient.shutdown();
    }

    @Test
    public void testCluster(){
        RedisClusterClient redisClusterClient = RedisClusterClient.create(RedisURI.Builder.redis("192.168.56.102",6379).build());
        StatefulRedisClusterConnection clusterConnection = redisClusterClient.connect();

        RedisAdvancedClusterAsyncCommands<String,String> redisAdvancedClusterAsyncCommands = clusterConnection.async();

        List<RedisFuture<String>> futures = Lists.newArrayList();
        for (int i = 0; i < 20; i++) {
            RedisFuture<String> future = redisAdvancedClusterAsyncCommands.set("foo" + i , "bar" + i);
            futures.add(future);
        }
        futures.forEach(future -> {
            try {
                System.err.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        AsyncNodeSelection<String,String> asyncNodeSelectionMaster = redisAdvancedClusterAsyncCommands.masters();
        AsyncExecutions<List<String>> strings = asyncNodeSelectionMaster.commands().keys("*");
        strings.forEach(s -> s.thenAccept(val -> System.err.println(val)));
        AsyncNodeSelection<String,String> asyncNodeSelection = redisAdvancedClusterAsyncCommands.slaves();
        AsyncExecutions<List<String>> executions = asyncNodeSelection.commands().keys("*");
        executions.forEach(result -> result.thenAccept(val -> System.err.println(val)));

    }


    @Test
    public void testConnectionPool(){

        RedisClusterClient redisClusterClient = RedisClusterClient.create(RedisURI.Builder.redis("192.168.56.102",6379).build());



    }


}
