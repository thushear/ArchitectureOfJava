package com.github.thushear;

import com.github.thushear.msf.consumer.Consumer;
import com.github.thushear.msf.service.HelloService;
import junit.framework.TestCase;

/**
 * Created by kongming on 2016/11/9.
 */
public class ConsumerAppTest extends TestCase {


    public void testConsumer() throws InterruptedException {

        Consumer<HelloService>  helloServiceConsumer = new Consumer<>(HelloService.class.getName());
        HelloService helloService = helloServiceConsumer.refer();

        while (true){
            Thread.sleep(1000);
            String sayResult = helloService.say("hello world");
            System.out.println("sayResult = " + sayResult);
        }



    }


}
