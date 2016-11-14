package com.github.thushear.proxy.javassist;

import com.github.thushear.proxy.Emptys;
import com.github.thushear.proxy.Invocation;
import com.github.thushear.proxy.ObjectUtil;
import org.apache.commons.lang.ArrayUtils;

import java.lang.reflect.Method;

/**
 * Created by kongming on 2016/11/4.
 */
public abstract class JavassistInvocation implements Invocation {

    private final Object proxy;

    private final Object target;

    private final Method method;

    private final Object[] arguments;

    public JavassistInvocation(Object proxy, Object target, Method method, Object[] arguments) {
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.arguments = ObjectUtil.defaultIfNull(ArrayUtils.clone(arguments), Emptys.EMPTY_OBJECT_ARRAY);
    }


    @Override
    public Object[] getArguments() {
        return new Object[0];
    }

    @Override
    public Method getMethod() {
        return null;
    }

    @Override
    public Object getProxy() {
        return null;
    }

    public final Object getTarget() {
        return target;
    }


}
