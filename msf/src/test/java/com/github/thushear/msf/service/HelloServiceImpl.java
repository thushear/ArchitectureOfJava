package com.github.thushear.msf.service;

/**
 * Created by kongming on 2016/10/28.
 */
public class HelloServiceImpl implements HelloService {


    @Override
    public void say(String text) {
        System.out.println("hello " + text + " !");
    }
}
