package com.github.thushear.springboot.react;

import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * <pre>

 * Created: 2018年03月16日 下午 18:34
 * Version: 1.0
 * Project Name: architecture
 * Last Edit Time: 2018年03月16日 下午 18:34
 * Update Log:
 * Comment:
 * </pre>
 */
public class MonoCase {


    public static void main(String[] args) {

        Mono mono = Mono.just("monoa");
        mono.subscribe(System.err::println);

        Mono.fromSupplier(()->{return "monobbb";}).subscribe(System.err::println);

        Mono.delay(Duration.ofSeconds(5)).subscribe(System.err::println);

        Mono.just("delaymono").delaySubscription(Duration.ofSeconds(5)).subscribe(System.err::println);


    }


}
