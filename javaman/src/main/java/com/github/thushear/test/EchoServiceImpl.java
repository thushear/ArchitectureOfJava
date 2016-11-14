package com.github.thushear.test;

/**
 * Created by kongming on 2016/11/3.
 */
public class EchoServiceImpl implements EchoService {


    @Override
    public String echo(String name) {
        return name;
    }

    @Override
    public Object echo(Object echo) {
        return null;
    }


}
