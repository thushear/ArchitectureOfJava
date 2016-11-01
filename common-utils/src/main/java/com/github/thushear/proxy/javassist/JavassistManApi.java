package com.github.thushear.proxy.javassist;

import javassist.*;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Created by kongming on 2016/11/1.
 */
public class JavassistManApi  {


    public static void main(String[] args) throws IOException, CannotCompileException, NotFoundException, InstantiationException, IllegalAccessException, ClassNotFoundException {
//        testReadAndWriteByteCode();

//        testClassLoader();

        testMethod();
    }


    /**]
     * Introspection and customization
     */

    public static void testMethod() throws NotFoundException, CannotCompileException, IOException {

        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.github.thushear.proxy.javassist.Point");
        CtMethod ctMethod = cc.getDeclaredMethod("move");
        ctMethod.insertBefore("{System.out.println($1);System.out.println($2);}");
        cc.writeFile(JavassistManApi.class.getResource("/").getPath());
    }


    /**
     *  Class loader
     */

    public static void testClassLoader() throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, ClassNotFoundException, IOException {
//        JavassistMock javassistMockTest = new JavassistMock(); error
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("com.github.thushear.proxy.javassist.JavassistMock");
        CtMethod ctMethod = ctClass.getDeclaredMethod("say");
        ctMethod.insertBefore("{System.out.println(\"JavassistMock.say()\");}");
        Class c  = ctClass.toClass();
        JavassistMock javassistMock = (JavassistMock) c.newInstance();
        javassistMock.say();

        //Using javassist.Loader

        Loader loader = new Loader(pool);
//        loader.addTranslator(pool,new MyTranslator());
        Class ctClassLoad =  loader.loadClass("com.github.thushear.proxy.javassist.JavassistMock");
        ctClassLoad.newInstance();

        //Modifying a system class

        CtClass cc = pool.get("java.lang.String");
        CtField f = new CtField(CtClass.intType, "hiddenValue", cc);
        f.setModifiers(Modifier.PUBLIC);
        cc.addField(f);
        cc.writeFile(JavassistManApi.class.getResource("/").getPath());
    }



    /**
     * Tut 01  Reading and writing bytecode
     */
    public static void testReadAndWriteByteCode() throws NotFoundException, CannotCompileException, IOException, IllegalAccessException, InstantiationException {

        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("com.github.thushear.proxy.javassist.JavassistManApi");
        ctClass.setSuperclass(pool.get("com.github.thushear.proxy.javassist.JavassistMock"));
        // 复制方法 向字节码动态添加方法
        CtMethod fMethod = ctClass.getDeclaredMethod("f");
        CtMethod gMethod = CtNewMethod.copy(fMethod,"g",ctClass,null);
        ctClass.addMethod(gMethod);



        //Defining a new class
        CtClass ctClassPoint = pool.makeClass("Point");


        System.out.println("byte code:" + Arrays.toString(ctClass.toBytecode()));
        ctClass.writeFile(JavassistManApi.class.getResource("/").getFile());
        ctClassPoint.writeFile(JavassistManApi.class.getResource("/").getPath());
    }


    public int f(int i){
        return i + i;
    }

}

class  MyTranslator implements Translator{

    @Override
    public void start(ClassPool pool) throws NotFoundException, CannotCompileException {

    }

    @Override
    public void onLoad(ClassPool pool, String classname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(classname);
        cc.setModifiers(Modifier.PUBLIC);
    }
}

class Point {
    int x, y;
    void move(int dx, int dy) { x += dx; y += dy; }
}