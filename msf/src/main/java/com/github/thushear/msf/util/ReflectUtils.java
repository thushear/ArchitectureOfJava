package com.github.thushear.msf.util;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kongming on 2016/11/1.
 */
public final class ReflectUtils {

    private static ConcurrentHashMap<String,Method> methodCache = new ConcurrentHashMap<>();


    /**
     * 根据类名 方法名 参数类型获取Method
     * @param clazzName
     * @param methodName
     * @param argsClasses
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    public static Method getMethod(String clazzName,String methodName,String[] argsClasses) throws ClassNotFoundException, NoSuchMethodException {

        StringBuilder sb = new StringBuilder();
        sb.append(clazzName).append("#").append(methodName).append("(");
        if (argsClasses != null && argsClasses.length > 0){
            for (String argsClass : argsClasses) {
                sb.append(argsClass).append(",");
            }
            sb.deleteCharAt(sb.length() -1);
        }
        sb.append(")");
        String key = sb.toString();
        Method method = methodCache.get(key);
        if (method == null){
            Class clazz = ClassLoaderUtils.forName(clazzName);
            Class[] argClasses = ClassLoaderUtils.getClasses(argsClasses);
            method = clazz.getMethod(methodName,argClasses);
            methodCache.putIfAbsent(key,method);
        }

        return method;
    }



}
