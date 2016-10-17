package com.github.thushear.msf.util;

/**
 * Created by kongming on 2016/10/14.
 */
public class ClassLoaderUtils {

  public static Class forName(String className) throws ClassNotFoundException {
    return Class.forName(className);
  }

  public static <T> T newInstance(Class clazz) throws IllegalAccessException, InstantiationException {
     return (T) clazz.newInstance();
  }

}
