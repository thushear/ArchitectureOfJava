package com.github.thushear.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

/**
 * Created by kongming on 2016/10/19.
 */
public class ReflectLearn {


  public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
//     Method[] methods = String.class.getMethods();
//    for (Method method : methods) {
//      System.out.println(method.getName());
//      System.out.println(method.getParameterTypes());
//      for (Class<?> aClass : method.getParameterTypes()) {
//        System.out.println("aClass = " + aClass.getName());
//      }
//    }
//    String str  = "method=substring";
//    Method getMethod = String.class.getMethod("substring",int.class,int.class);
//    System.out.println(getMethod.getName());
//    Object result = getMethod.invoke("aaaa",0,2);
//    System.out.println("result = " + result);
//
//    System.out.println("int.class.getName() = " + int.class.getName());
//    System.out.println("int.class.getName() = " + int.class.getTypeName());
//    Class clazz = forName("int");
//    Class clazz1 = forName("java.lang.String");
//    System.out.println("clazz1 = " + clazz1);
//    System.out.println("clazz = " + clazz);

    // basic info

    Class integerClass = int.class;
    printInfo(integerClass);

    Class intArrayClass = int[].class;
    printInfo(intArrayClass);

    Class boxIntegerClass = Integer.class;

    printInfo(boxIntegerClass);

    Class boxIntegerArrayClass = Integer[].class;

    printInfo(boxIntegerArrayClass);

    Class StringClass = String.class;

    printInfo(StringClass);

    Class StringArrayClass = String[].class;

    printInfo(StringArrayClass);

    Class  executorServiceClass = ExecutorService.class;

    printInfo(executorServiceClass);

    Class  executorServiceArrayClass = ExecutorService[].class;

    printInfo(executorServiceArrayClass);


    // Class.forname

    Class.forName("[I");

    Class forNameIntegerArray = Class.forName("[Ljava.lang.Integer;");
    printInfo(forNameIntegerArray);

    Class forNameString = Class.forName("java.lang.Integer");
    printInfo(forNameString);

    Class forNameStringArray = Class.forName("[Ljava.lang.Integer;");
    printInfo(forNameStringArray);

    //
    boolean isAssign = Number.class.isAssignableFrom(Integer.class);
    System.out.println("isAssign = " + isAssign);

  }

  private static void printInfo(Class aClass) {
    System.out.printf("simplename %s cannonicalName %s name %s typename %s isPrimitive %s isArray %s  getComponentType %s isInterface %s \n",aClass.getSimpleName()
            ,aClass.getCanonicalName(),aClass.getName(),aClass.getTypeName(),aClass.isPrimitive(),aClass.isArray(),aClass.getComponentType(),aClass.isInterface());
//    System.out.println("getSimpleName="  aClass.getSimpleName() + ":" + aClass.getCanonicalName() + ":" + aClass.getName() + ":" + aClass.getTypeName() + ":" + aClass.isPrimitive() + ":" + aClass.isArray());
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
