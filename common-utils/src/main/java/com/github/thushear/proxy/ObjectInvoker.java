package com.github.thushear.proxy;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by kongming on 2016/11/2.
 */
public interface ObjectInvoker  extends Serializable{

    Object invoke(Object proxy, Method method,Object... arguments) throws Throwable;

}
