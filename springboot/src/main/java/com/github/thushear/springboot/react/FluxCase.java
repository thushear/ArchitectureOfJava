package com.github.thushear.springboot.react;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>

 * Created: 2018年03月15日 下午 17:36
 * Version: 1.0
 * Project Name: architecture
 * Last Edit Time: 2018年03月15日 下午 17:36
 * Update Log:
 * Comment:
 * </pre>
 */
public class FluxCase {


    public static void main(String[] args) throws InterruptedException {

        List<String> list = new ArrayList<>();
        list.add("one");list.add("two");list.add("three");

        Flux.just("hello","world").subscribe(System.out::println);

        Flux<String> fluxString = Flux.fromIterable(list);

        fluxString.subscribe(System.out::println);

//        Flux.interval(Duration.ofMillis(1000)).subscribe(System.out::println);

        Flux.range(1,10).subscribe(System.out::println);

        Flux.empty().subscribe(System.out::println);

//        Flux.error(new NullPointerException()).subscribe(System.out::println);


        Flux.generate(synchronousSink -> {
            synchronousSink.next(1);
            System.err.println("sink1");
            synchronousSink.complete();
        })
                .subscribe(System.out::println);


        List intList = new ArrayList();

        Flux.generate(synchronousSink -> {
            synchronousSink.next(1);
            intList.add(1);
            if (intList.size() == 10){
                synchronousSink.complete();
            }

        })
                .subscribe(System.out::println);

        Flux.create(fluxSink -> {
            for (int i = 0; i < 10; i++) {
                fluxSink.next(i);
            }
            fluxSink.complete();
                }
        ).subscribe(System.err::println);

        String a = "a";

        Flux.just(a).subscribe(System.err::println);

        Thread.sleep(2000L);

        a = "aaaaa";


        System.err.println("----------------------------------------");
        Flux.range(1,100).buffer(20).subscribe(System.err::println);
        System.err.println("========================================");
        Flux.range(1,100).bufferUntil(i -> i % 3 == 0).subscribe(System.err::println);

        System.err.println("----------------------------------------");
        Flux.range(1,100).filter(i -> i % 3 ==0).subscribe(System.err::println);
        System.err.println("========================================");
        Flux.range(1,100).window(20).subscribe(System.err::println);
        System.err.println("----------------------------------------");

        Flux.just("a","b").zipWith(Flux.just("c","d")).subscribe(System.err::println);

        System.err.println("========================================");

        Flux.just("e","f").zipWith(Flux.just("g","h"), (s1,s2) -> String.format("%s-%s",s1,s2) ).subscribe(System.err::println);




        // 消息处理
        System.err.println("----------------------------------------");
        Flux.just("a","b").concatWith(Mono.error(new NullPointerException())).subscribe(System.out::println,System.err::println);
        System.err.println("========================================");
        Flux.just("a","b").concatWith(Mono.error(new NullPointerException())).onErrorReturn("0").subscribe(System.out::println,System.err::println);


        //调度器


        System.err.println("----------------------------------------");

        Flux<String> publisher = Flux.just("a");



        publisher.subscribe(ele -> {
            System.err.println("subscribe: " + ele);
        });


        publisher.concatWith(Flux.just("bb","cc"));
        publisher.publish();

        System.err.println("========================================");

        System.err.println("----------------------------------------");

        Flux pub = Flux.generate(synchronousSink -> {

            synchronousSink.next("pub");

            synchronousSink.complete();
        });

        SimpleSubscriber simpleSubscriber = new SimpleSubscriber();
        pub.subscribe(simpleSubscriber);
        System.err.println("========================================");

        synchronized (FluxCase.class){
            try {
                FluxCase.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
class SimpleSubscriber extends  BaseSubscriber{

    @Override
    protected void hookOnNext(Object value) {
        System.err.println("hookon Next:" + value);
        request(1);
    }

    @Override
    protected void hookOnComplete() {
        super.hookOnComplete();

    }

    @Override
    protected void hookOnError(Throwable throwable) {
        super.hookOnError(throwable);
    }

    @Override
    protected void hookOnCancel() {
        super.hookOnCancel();
    }
}