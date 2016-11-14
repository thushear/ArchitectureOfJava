package com.github.thushear.proxy;

import java.io.Serializable;

/**
* Created by kongming on 2016/11/4.
        */
public interface Interceptor extends Serializable {

    Object intercept(Invocation invocation) throws Throwable;

}
