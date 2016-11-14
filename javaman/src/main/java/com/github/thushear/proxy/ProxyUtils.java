package com.github.thushear.proxy;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Created by kongming on 2016/11/3.
 */
public class ProxyUtils {

    private static final Map<Class<?>, Class<?>> WRAPPER_CLASS_MAP;


    static {
        Map<Class<?>, Class<?>> wrappers = Maps.newHashMapWithExpectedSize(8);
        // @see ClassUtil
        wrappers.put(Integer.TYPE, Integer.class);
        wrappers.put(Character.TYPE, Character.class);
        wrappers.put(Boolean.TYPE, Boolean.class);
        wrappers.put(Short.TYPE, Short.class);
        wrappers.put(Long.TYPE, Long.class);
        wrappers.put(Float.TYPE, Float.class);
        wrappers.put(Double.TYPE, Double.class);
        wrappers.put(Byte.TYPE, Byte.class);
        WRAPPER_CLASS_MAP = Collections.unmodifiableMap(wrappers);


    }


    public static String getJavaClassName(Class<?> clazz) {
        if (clazz.isArray()) {
            return getJavaClassName(clazz.getComponentType()) + "[]";
        }

        return clazz.getName();
    }



    public static Class<?> getWrapperClass(Class<?> primitiveType) {
        return WRAPPER_CLASS_MAP.get(primitiveType);
    }


    public static boolean isEqualsMethod(Method method){
        return "equals".equals(method.getName()) && Boolean.TYPE.equals(method.getReturnType())
                && method.getParameterTypes().length == 1 && Object.class.equals(method.getParameterTypes()[0]);
    }

    public static boolean isHashCode(Method method) {
        return "hashCode".equals(method.getName()) && Integer.TYPE.equals(method.getReturnType())
                && method.getParameterTypes().length == 0;
    }


}
