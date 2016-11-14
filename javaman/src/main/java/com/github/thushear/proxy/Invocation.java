package com.github.thushear.proxy;

import java.lang.reflect.Method;

/**
 * Created by kongming on 2016/11/4.
 */
public interface Invocation {


    /**
     * 获取方法调用参数
     *
     * @return 方法调用参数
     */
    Object[] getArguments();

    /**
     * 返回方法对象
     *
     * @return 方法对象
     */
    Method getMethod();

    /**
     * 返回代理对象
     *
     * @return 代理对象
     */
    Object getProxy();

    /**
     * 触发拦截器调用链
     *
     * @return 调用结果
     *
     * @throws Throwable
     */
    Object proceed() throws Throwable;
}
