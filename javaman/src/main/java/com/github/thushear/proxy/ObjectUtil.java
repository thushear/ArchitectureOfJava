package com.github.thushear.proxy;

/**
 * Created by kongming on 2016/11/4.
 */
public class ObjectUtil {

    public static <T, S extends T> T defaultIfNull(T object, S defaultValue) {
        return object == null ? defaultValue : object;
    }

}
