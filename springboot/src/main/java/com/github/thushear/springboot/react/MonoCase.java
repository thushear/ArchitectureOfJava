package com.github.thushear.springboot.react;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.List;

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


    public static class Contributor {
        public final String login;
        public final int contributions;

        public Contributor(String login, int contributions) {
            this.login = login;
            this.contributions = contributions;
        }
    }


    public static void main(String[] args) {


        Flux<List > conFlux1 = WebClient.create("https://api.github.com/repos/square/retrofit/contributors").get().retrieve().bodyToFlux(List.class );
        Flux<List > conFlux2 = WebClient.create("https://api.github.com/repos/square/retrofit/contributors").get().retrieve().bodyToFlux(List.class );


        Flux merge = Flux.zip(conFlux1,conFlux2 ).map(tuple2 -> {
            System.err.println(tuple2.getT1());
            System.err.println(tuple2.getT2());
            return tuple2.getT1().addAll(tuple2.getT2());
        } );
        merge.blockLast();
        merge.subscribe(System.err::println);

//        Mono mono = Mono.just("monoa");
//        mono.subscribe(System.err::println);
//
//        Mono.fromSupplier(()->{return "monobbb";}).subscribe(System.err::println);
//
//        Mono.delay(Duration.ofSeconds(5)).subscribe(System.err::println);
//
//        Mono.just("delaymono").delaySubscription(Duration.ofSeconds(5)).subscribe(System.err::println);
//
//
//        Mono.zip( Mono.just("aaa"),Mono.fromSupplier( () ->{
//            try {
//                Thread.sleep(1000);
//            }catch (Exception e){
//
//            }
//            return "bbb";
//        } )).map(Tuple2::getT1  ).log().subscribe(System.err::println);

    }


}
