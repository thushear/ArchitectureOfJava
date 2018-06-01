package com.github.thushear.async.java;

import com.google.common.collect.Lists;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
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


        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> System.err.println("hello"));
        System.err.println("void:" + voidCompletableFuture.get());

        CompletableFuture<String> asyncFuture = CompletableFuture.supplyAsync(() -> "hello");
        asyncFuture.complete("world");
        System.err.println("async:" + asyncFuture.get());

        asyncFuture.thenAccept(t-> System.err.println("t:" + t));

        asyncFuture.thenAcceptAsync(t-> System.err.println("tt:" + t)).thenRun(()-> System.err.println("tt run"));

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(()->{
            return atomicInteger.incrementAndGet();
        });
        System.out.println(completableFuture.isDone());
        System.out.println(completableFuture.get());


        // map
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> 100);
        integerCompletableFuture.thenApply( i -> new Long(i) ).thenApply(l -> new Double(l))
                .thenApply(d -> String.valueOf(d) + " last")
                .thenAcceptAsync(res -> System.err.println("res:" + res));


        // compose
        CompletableFuture<String> firstFuture = CompletableFuture.supplyAsync(() -> " first " )
                .thenApply(s -> s + "future");
        firstFuture = firstFuture.thenComposeAsync(s -> CompletableFuture.supplyAsync(() -> s + " second "));
        firstFuture = firstFuture.thenComposeAsync(s -> CompletableFuture.supplyAsync(() -> s + " third "));
        System.err.println("thenCompose:" + firstFuture.get());

        //thenCombine()
        CompletableFuture<String> combinedFuture =  CompletableFuture.supplyAsync(() -> " combine  ").thenApply(s -> s + " future" );
        CompletableFuture completableFuture1 = firstFuture.thenCombineAsync(combinedFuture, (s,i) ->{return s + i;} );
        System.err.println("combinedFuture:" + completableFuture1.get());

        CompletableFuture  whenCompleteFuture = firstFuture.whenCompleteAsync( (t,u) -> System.err.println("t" + t) );
        System.err.println("whenCompleteFuture:" + whenCompleteFuture.get());

        CompletableFuture  handleFuture = firstFuture.handle( (s,t) -> s!=null? s : t );
        System.err.println("handleFuture:" + handleFuture.get());

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(CompletableFutureMan::getMoreData);
        Future<Integer> f = future.whenComplete((t, u)->{
            System.out.println(t);
            System.out.println(u);
        });
        System.out.println(f.get());


        System.err.println("=============================================");
        List<String> strList = Lists.newArrayList("1","2","3","4","5");
        List<CompletableFuture<Integer>>  futureList = Lists.newArrayList();
        CompletableFuture.supplyAsync(() -> strList)
                .thenApplyAsync( list -> {
                    for (String single : list) {
                        futureList.add(CompletableFuture.supplyAsync( () -> {
                            return single.length();
                        } ));
                    }
                    return futureList;
                }).whenCompleteAsync((l,t) -> {
                        if (t != null){
                            System.err.println("ttttt:" + t);
                        }else {
                            for (CompletableFuture<Integer> ff : l) {
                                try {
                                    System.err.println("fu:" +  ff.get());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
        } );






        System.err.println("=============================================");


        Random random = new Random();

        CompletableFuture<String> one = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "1";
        });

        CompletableFuture<String> two = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "2";
        });

        CompletableFuture.allOf(one,two);


        System.err.println("=============================================");

        System.err.println("=============================================");

        long start = System.currentTimeMillis();
        getDiskIO().thenCombineAsync(getSocketIO(),(a,b)->{
            System.err.println("a:" + a);
            System.err.println("b:" + b);
            System.err.println("cost time :" + (System.currentTimeMillis() - start));
            return a + b;
        }).whenComplete((t, u)->{
            System.out.println(t);
            System.out.println(u);
        }).get();
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



    static CompletableFuture<String> getDiskIO(){


        return CompletableFuture.supplyAsync( () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "111";
        } );


    }


    static CompletableFuture<String> getSocketIO(){


        return CompletableFuture.supplyAsync( () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "222";
        } );


    }

}
