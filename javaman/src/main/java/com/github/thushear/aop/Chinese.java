package com.github.thushear.aop;

import org.springframework.stereotype.Component;

/**
 * 说明:
 * User: kongming
 * Date: 14-6-5
 * Time: 下午2:51
 */
@Component
public class Chinese implements Person{


    public String sayHello(String name){
        System.out.println("--正在执行sayHello---");
        return name + "Hello spring Aop";
    }


}
