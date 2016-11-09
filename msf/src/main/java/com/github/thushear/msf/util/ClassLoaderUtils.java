package com.github.thushear.msf.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by kongming on 2016/10/14.
 */
public class ClassLoaderUtils {

  private static Set<Class> primitiveSet = new HashSet<Class>();

  static {
    primitiveSet.add(Integer.class);
    primitiveSet.add(Long.class);
    primitiveSet.add(Float.class);
    primitiveSet.add(Byte.class);
    primitiveSet.add(Short.class);
    primitiveSet.add(Double.class);
    primitiveSet.add(Character.class);
    primitiveSet.add(Boolean.class);
  }


  /**
   * get name.
   * java.lang.Object[][].class => "java.lang.Object[][]"
   *
   * @param c class.
   * @return name.
   */
  public static String getName(Class<?> c)
  {
    if( c.isArray() )
    {
      StringBuilder sb = new StringBuilder();
      do
      {
        sb.append("[]");
        c = c.getComponentType();
      }
      while( c.isArray() );

      return c.getName() + sb.toString();
    }
    return c.getName();
  }


  /**
   * Class[]转String[] <br>
   * 注意，得到的String可能不能直接用于Class.forName，请使用getClass(String)反向获取
   *
   * @param types
   *         Class[]
   * @return 对象描述
   */
  public static String[] getTypeStrs(Class[] types) {
    if (CommonUtils.isEmpty(types)) {
      return new String[0];
    } else {
      String[] strings = new String[types.length];
      for (int i = 0; i < types.length; i++) {
        strings[i] = getTypeStr(types[i]);
      }
      return strings;
    }
  }


  public static String getTypeStr(Class clazz){
    String typeStr;
    if (clazz.isArray()){
      String name = clazz.getName();
      typeStr = jvmNameToCanonicalName(name);
    }else {
      typeStr = clazz.getName();
    }
    return typeStr;
  }

  private static String jvmNameToCanonicalName(String jvmName) {
    boolean isarray = jvmName.charAt(0) == '[';
    if (isarray) {
      String cnName = ""; // 计数，看上几维数组
      int i = 0;
      for (; i < jvmName.length(); i++) {
        if (jvmName.charAt(i) != '[') {
          break;
        }
        cnName += "[]";
      }
      String componentType = jvmName.substring(i, jvmName.length());
      if ("Z".equals(componentType)) cnName = "boolean" + cnName;
      else if ("B".equals(componentType)) cnName = "byte" + cnName;
      else if ("C".equals(componentType)) cnName = "char" + cnName;
      else if ("D".equals(componentType)) cnName = "double" + cnName;
      else if ("F".equals(componentType)) cnName = "float" + cnName;
      else if ("I".equals(componentType)) cnName = "int" + cnName;
      else if ("J".equals(componentType)) cnName = "long" + cnName;
      else if ("S".equals(componentType)) cnName = "short" + cnName;
      else cnName = componentType.substring(1,componentType.length()-1) + cnName; // 对象的 去掉L
      return cnName;
    }
    return jvmName;
  }


  /**
   * 转换参数字符数组为class对象数组
   * @param typeStr
   * @return
     */
  public static Class[] getClasses(String[] typeStr){

     if (typeStr == null || typeStr.length == 0){
       return new Class[0];
     }
     Class[] classes = new Class[typeStr.length];
    for (int i = 0; i < typeStr.length; i++) {
       classes[i] = forName(typeStr[i]);
    }
    return classes;
  }





  /**
   * 实例化一个对象(只检测默认构造函数，其它不管）
   *
   * @param clazz
   *         对象类
   * @param <T>
   *         对象具体类
   * @return 对象实例
   * @throws Exception 没有找到方法，或者无法处理，或者初始化方法异常等
   */
  public static <T> T newInstance(Class<T> clazz) throws Exception {
    if(primitiveSet.contains(clazz)){
      return null;
    }
    if (clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers())) {
      Constructor constructorList[] = clazz.getDeclaredConstructors();
      Constructor defaultConstructor = null;
      for (Constructor con : constructorList) {
        if (con.getParameterTypes().length == 1) {
          defaultConstructor = con;
          break;
        }
      }
      if (defaultConstructor != null) {
        if (defaultConstructor.isAccessible()) {
          return (T) defaultConstructor.newInstance(new Object[]{null});
        } else {
          try {
            defaultConstructor.setAccessible(true);
            return (T) defaultConstructor.newInstance(new Object[]{null});
          } finally {
            defaultConstructor.setAccessible(false);
          }
        }
      } else {
        throw new Exception("The " + clazz.getCanonicalName() + " has no default constructor!");
      }
    }
    try {
      return clazz.newInstance();
    } catch (Exception e) {
      Constructor<T> constructor = clazz.getDeclaredConstructor();
      if (constructor.isAccessible()) {
        throw new Exception("The " + clazz.getCanonicalName() + " has no default constructor!", e);
      } else {
        try {
          constructor.setAccessible(true);
          return constructor.newInstance();
        } finally {
          constructor.setAccessible(false);
        }
      }
    }
  }



  /**
   * void(V).
   */
  public static final char JVM_VOID = 'V';

  /**
   * boolean(Z).
   */
  public static final char JVM_BOOLEAN = 'Z';

  /**
   * byte(B).
   */
  public static final char JVM_BYTE = 'B';

  /**
   * char(C).
   */
  public static final char JVM_CHAR = 'C';

  /**
   * double(D).
   */
  public static final char JVM_DOUBLE = 'D';

  /**
   * float(F).
   */
  public static final char JVM_FLOAT = 'F';

  /**
   * int(I).
   */
  public static final char JVM_INT = 'I';

  /**
   * long(J).
   */
  public static final char JVM_LONG = 'J';

  /**
   * short(S).
   */
  public static final char JVM_SHORT = 'S';



  private static final ConcurrentMap<String, Class<?>> NAME_CLASS_CACHE = new ConcurrentHashMap<String, Class<?>>();


  public static Class<?> forName(String name) {
    try {
      return name2class(name);
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException("Not found class " + name + ", cause: " + e.getMessage(), e);
    }
  }

  /**
   * 得到当前ClassLoader
   *
   * @return ClassLoader
   */
  public static ClassLoader getCurrentClassLoader() {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    if (cl == null) {
      cl = ClassLoaderUtils.class.getClassLoader();
    }
    return cl == null ? ClassLoader.getSystemClassLoader() : cl;
  }

  /**
   * 得到当前ClassLoader
   * @param clazz 某个类
   * @return ClassLoader
   */
  public static ClassLoader getClassLoader(Class<?> clazz) {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    if (loader != null) {
      return loader;
    }
    if (clazz != null) {
      loader = clazz.getClassLoader();
      if (loader != null) {
        return loader;
      }
      return clazz.getClassLoader();
    }
    return ClassLoader.getSystemClassLoader();
  }


  /**
   * name to class.
   * "boolean" => boolean.class
   * "java.util.Map[][]" => java.util.Map[][].class
   *
   * @param name name.
   * @return Class instance.
   */
  public static Class<?> name2class(String name) throws ClassNotFoundException
  {
    return name2class(ClassLoaderUtils.getCurrentClassLoader(), name);
  }


  /**
   * name to class.
   * "boolean" => boolean.class
   * "java.util.Map[][]" => java.util.Map[][].class
   *
   * @param cl ClassLoader instance.
   * @param name name.
   * @return Class instance.
   */
  public static Class<?> name2class(ClassLoader cl, String name) throws ClassNotFoundException
  {
    int c = 0, index = name.indexOf('[');
    if( index > 0 )
    {
      c = ( name.length() - index ) / 2;
      name = name.substring(0, index);
    }
    if( c > 0 )
    {
      StringBuilder sb = new StringBuilder();
      while( c-- > 0 )
        sb.append("[");

      if( "void".equals(name) ) sb.append(JVM_VOID);
      else if( "boolean".equals(name) ) sb.append(JVM_BOOLEAN);
      else if( "byte".equals(name) ) sb.append(JVM_BYTE);
      else if( "char".equals(name) ) sb.append(JVM_CHAR);
      else if( "double".equals(name) ) sb.append(JVM_DOUBLE);
      else if( "float".equals(name) ) sb.append(JVM_FLOAT);
      else if( "int".equals(name) ) sb.append(JVM_INT);
      else if( "long".equals(name) ) sb.append(JVM_LONG);
      else if( "short".equals(name) ) sb.append(JVM_SHORT);
      else sb.append('L').append(name).append(';'); // "java.lang.Object" ==> "Ljava.lang.Object;"
      name = sb.toString();
    }
    else
    {
      if( "void".equals(name) ) return void.class;
      else if( "boolean".equals(name) ) return boolean.class;
      else if( "byte".equals(name) ) return byte.class;
      else if( "char".equals(name) ) return char.class;
      else if( "double".equals(name) ) return double.class;
      else if( "float".equals(name) ) return float.class;
      else if( "int".equals(name) ) return int.class;
      else if( "long".equals(name) ) return long.class;
      else if( "short".equals(name) ) return short.class;
    }

    if( cl == null )
      cl = Thread.currentThread().getContextClassLoader();
    Class<?> clazz = NAME_CLASS_CACHE.get(name);
    if(clazz == null){
      clazz = Class.forName(name, true, cl);
      NAME_CLASS_CACHE.put(name, clazz);
    }
    return clazz;
  }










}
