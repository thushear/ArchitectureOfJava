package com.github.thushear.async.rxjava;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by kongming on 2016/11/10.
 */
public class RxJavaMan {


    public static void main(String[] args) {

//        hello("ben","trump");


        //
//        Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {
//
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("hello world!");
//                subscriber.onCompleted();
//            }
//        });

        //简化版本
        Observable<String> myObservable = Observable.just("Hello, world!");


        Subscriber<String> mySubsriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("subscribe" + "onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("subscribe" + s);
            }
        };

        myObservable.subscribe(mySubsriber);

    }






    public static void hello(String... names){
        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("hello "  +  s  + " !");
            }
        });
    }


}
