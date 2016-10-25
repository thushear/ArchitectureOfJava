package com.github.thushear.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by kongming on 2016/10/25.
 */
public class ClassAPIMan {


    public static void main(String[] args) {
        // Class.getName()
        testGetName();

        /**
         * 如果 name 表示一个基本类型或 void，则会尝试在未命名的包中定位用户定义的名为 name 的类。因此，该方法不能用于获得表示基本类型或 void 的任何 Class 对象。

         如果 name 表示一个数组类，则会加载但不初始化该数组类的组件类型。
         */
        // Class.forName
        /**
         * 如果此类对象表示一个数组类，则名字的内部形式为：表示该数组嵌套深度的一个或多个 '[' 字符加元素类型名。元素类型名的编码如下：

         Element Type	   	Encoding
         boolean	   	Z
         byte	   	B
         char	   	C
         class or interface	   	Lclassname;
         double	   	D
         float	   	F
         int	   	I
         long	   	J
         short	   	S
         */

        Class intClass = forName("int");
        System.out.println(intClass.getName());
        Class intArrayClass = forName("int[]");
        System.out.println(intArrayClass.getName());


        /**
         * 判定指定的 Class 对象是否表示一个基本类型。
         有九种预定义的 Class 对象，表示八个基本类型和 void。这些类对象由 Java 虚拟机创建，与其表示的基本类型同名，即 boolean、byte、char、short、int、long、float 和 double。
         */
        isPrimitive(Integer.class);
        isPrimitive(int.class);
        isPrimitive(void.class);

        /**
         * isArray
         */
        isArray((new Object[1]).getClass());
        isArray(String.class);
        isArray(Array.class);

        /**
         * isAssignableFrom 判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，或是否是其超类或超接口。如果是则返回 true；否则返回 false。如果该 Class 表示一个基本类型，且指定的 Class 参数正是该 Class 对象，则该方法返回 true；否则返回 false。
         */
        isAssignableFrom(Object.class,String.class);
        isAssignableFrom(String.class,Object.class);

        /**
         * getMethod
         */
        Method[] arrayMethods = Array.class.getMethods();
        for (Method arrayMethod : arrayMethods) {
            System.out.print(arrayMethod.getName() + " : ");
        }
        System.out.println();




    }






    public static void isAssignableFrom(Class source,Class des){
        System.out.println(source.isAssignableFrom(des));
    }


    public static void isArray(Class clazz){
        System.out.println(clazz.getName() + ":" + clazz.isArray() + ":" + clazz.getComponentType());
    }


    public static void isPrimitive(Class clazz){
        System.out.println(clazz.getName() + ":" + clazz.isPrimitive());
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
    private static Class<?> name2class(ClassLoader cl, String name) throws ClassNotFoundException
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
            cl = ClassLoaderUtils.getCurrentClassLoader();
        Class<?> clazz = NAME_CLASS_CACHE.get(name);
        if(clazz == null){
            clazz = Class.forName(name, true, cl);
            NAME_CLASS_CACHE.put(name, clazz);
        }
        return clazz;
    }



    public static void testGetName(){
        System.out.println(String.class.getName());
        System.out.println(int.class.getName());
        System.out.println(void.class.getName());
        System.out.println((new Object[3]).getClass().getName());
        System.out.println((new int[2][3][4]).getClass().getName());

    }





}
