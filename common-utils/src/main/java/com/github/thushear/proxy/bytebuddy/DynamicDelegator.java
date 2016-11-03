package com.github.thushear.proxy.bytebuddy;

import com.github.thushear.proxy.ObjectInvoker;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.Method;

/**
 * Created by kongming on 2016/11/3.
 */
public class DynamicDelegator {

    private static ObjectInvoker invoker;

    @RuntimeType
    public static Object invoke(@This Object proxy, @Origin Method method, @AllArguments Object[] arguments)
            throws Throwable {
        return invoker.invoke(proxy, method, arguments);
    }

    public static void setInvoker(ObjectInvoker invoker) {
        DynamicDelegator.invoker = invoker;
    }
}
