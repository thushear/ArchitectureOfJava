package com.github.thushear.aop;

import org.springframework.stereotype.Component;

/**
 * Created by kongming on 2016/11/4.
 */
@Component("aopClassSample")
public class AopClassSample {


    public String testAop(String name){
        System.out.println("aop" + name);
        return "1";
    }

}
