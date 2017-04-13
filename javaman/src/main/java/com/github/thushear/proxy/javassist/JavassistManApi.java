//package com.github.thushear.proxy.javassist;
//
//import com.github.thushear.proxy.Interceptor;
//import com.github.thushear.proxy.ObjectInvoker;
//import com.github.thushear.proxy.ProxyUtils;
//import com.github.thushear.test.EchoService;
//import com.google.common.collect.Maps;
//import com.google.common.collect.Sets;
//import javassist.*;
//import org.apache.commons.lang.ArrayUtils;
//
//import java.io.IOException;
//import java.io.Serializable;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//import java.util.Arrays;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * Created by kongming on 2016/11/1.
// */
//public class JavassistManApi  {
//
//
//    public static void main(String[] args) throws IOException, CannotCompileException, NotFoundException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
////        testReadAndWriteByteCode();
//
////        testClassLoader();
//
////        testMethod();
//
////        testCreateInvoker();
//        testCreateInterceptor();
//    }
//
//
//    static ClassPool classPool = new ClassPool();
//
//    static AtomicInteger atomicInteger = new AtomicInteger(1);
//
//    static {
//        classPool.appendClassPath(new LoaderClassPath(ClassLoader.getSystemClassLoader()));
//    }
//    private static final String GET_METHOD_NAME = "_javassistGetMethod";
//
//
//
//    /**
//     * Create Interceptor
//     */
//    public static void testCreateInterceptor() throws NotFoundException, CannotCompileException, IOException {
//        //创建类对象
//        CtClass ctClass = classPool.makeClass(JavassistManApi.class.getName() + atomicInteger.incrementAndGet(),classPool.get(Object.class.getName()));
//
//
//        Class<?>[] interfaces = new Class[2];
//        interfaces[0] = EchoService.class;
//        interfaces[1] = Serializable.class;
//        //添加接口
//        for (Class<?> aInterface : interfaces) {
//            ctClass.addInterface(resolve(aInterface));
//        }
//
//        //添加属性
//        ctClass.addField(new CtField(resolve(Object.class),"target",ctClass));
//        ctClass.addField(new CtField(resolve(Interceptor.class),"interceptor",ctClass));
//        CtMethod  getMethod =
//                new CtMethod(resolve(Method.class),"_javassistGetMethod"
//                        ,resolve(new Class[]{String.class,String.class,Class[].class}),ctClass);
//        String getMethodBody = "try{\n" +
//                "return com.github.thushear.proxy.javassist.JavassistManApi.getMethodCache($1,$2,$3);\n" +
//                "} catch(Exception e){\n" +
//                "throw new RuntimeException(\"unable to look up method,\",e);\n" +
//                "}";
//
//        getMethod.setBody(getMethodBody);
//        ctClass.addMethod(getMethod);
//
//        //add hashcode Method
//        CtMethod hashCodeMethod = new CtMethod(resolve(Integer.TYPE),"hashCode",new CtClass[0],ctClass);
//        hashCodeMethod.setBody("{\n\treturn System.identityHashCode(this);\n}");
//        ctClass.addMethod(hashCodeMethod);
//
//        // add equals method
//        CtMethod equalsMethod = new CtMethod(resolve(Boolean.TYPE),"equals",
//                resolve(new Class[]{Object.class}),ctClass);
//        String equalsBody = "{\n\treturn this==$1;\n}";
//        equalsMethod.setBody(equalsBody);
//        ctClass.addMethod(equalsMethod);
//
//        CtConstructor ctConstructor = new CtConstructor(resolve(new Class[]{Object.class,Interceptor.class}),ctClass);
//        ctConstructor.setBody("{\n" +
//                "\tthis.target = $1;\n" +
//                "\tthis.interceptor = $2; }");
//        ctClass.addConstructor(ctConstructor);
//        // add implementation methods
//        Method[] methods = EchoService.class.getMethods();
//
//        for (Method method : methods) {
//            CtMethod ctMethod = new CtMethod(resolve(method.getReturnType())
//                    ,method.getName(),resolve(method.getParameterTypes()),ctClass);
//
//            CtClass methodCtClass = classPool.makeClass(method.getDeclaringClass().getSimpleName() + "_" + method.getName() + "_invocation" + atomicInteger.incrementAndGet() ,resolve( JavassistInvocation.class));
//
//            CtConstructor constructor =
//                    new CtConstructor(resolve(new Class[] {Object.class, Object.class, Method.class,
//                            Object[].class}), methodCtClass);
//            constructor.setBody("{\n\tsuper($$);\n}");
//
//            methodCtClass.addConstructor(constructor);
//            CtMethod proceedMethod =
//                    new CtMethod(resolve(Object.class), "proceed", resolve(new Class[0]),
//                            methodCtClass);
//
//            Class<?>[] argumentTypes = method.getParameterTypes();
//            StringBuilder builder = new StringBuilder("{\n");
//            if (!Void.TYPE.equals(method.getReturnType())){
//                builder.append("\treturn ");
//                if (method.getReturnType().isPrimitive()){
//                    builder.append("new ");
//                    builder.append(ProxyUtils.getWrapperClass(method.getReturnType()).getName());
//                    builder.append("(");
//                }
//            }else {
//                builder.append("\t");
//            }
//            builder.append("( (");
//            builder.append(ProxyUtils.getJavaClassName(method.getDeclaringClass()));
//            builder.append(" )getTarget() ).");
//            builder.append(method.getName());
//            builder.append("(");
//            for (int i = 0; i < argumentTypes.length; ++i) {
//                final Class<?> argumentType = argumentTypes[i];
//                builder.append(createCastExpression(argumentType, "getArguments()[" + i + "]"));
//                if (i != argumentTypes.length - 1) {
//                    builder.append(", ");
//                }
//            }
//
//            if (!Void.TYPE.equals(method.getReturnType()) && method.getReturnType().isPrimitive()) {
//                builder.append(") );\n");
//            } else {
//                builder.append(");\n");
//            }
//
//            if (Void.TYPE.equals(method.getReturnType())) {
//                builder.append("\treturn null;\n");
//            }
//
//            builder.append("}");
//            String body = builder.toString();
//            proceedMethod.setBody(body);
//            methodCtClass.addMethod(proceedMethod);
//            Class  invocationClass =methodCtClass.toClass();
//            String methodbody =
//                    "{\n\t return ( $r ) interceptor.intercept( new " + invocationClass.getName()
//                            + "( this, target, " + GET_METHOD_NAME + "(\""
//                            + method.getDeclaringClass().getName() + "\", \"" + method.getName()
//                            + "\", $sig), $args ) );\n }";
//            ctMethod.setBody(methodbody);
//            ctClass.addMethod(ctMethod);
//            methodCtClass.writeFile(JavassistManApi.class.getResource("/").getPath());
//        }
//
//
//        ctClass.writeFile(JavassistManApi.class.getResource("/").getPath());
//        ctClass.toClass();
//
//    }
//
//
//
//    private static String createCastExpression(Class<?> type, String objectToCast) {
//        if (!type.isPrimitive()) {
//            return "( " + ProxyUtils.getJavaClassName(type) + " )" + objectToCast;
//        }
//
//        return "( ( " + ProxyUtils.getWrapperClass(type).getName() + " )" + objectToCast + " )." + type.getName()
//                + "Value()";
//    }
//
//
//
//    /**
//     * Create Invoker
//     */
//    public static  void testCreateInvoker() throws NotFoundException, CannotCompileException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        //创建类对象
//        CtClass ctClass = classPool.makeClass(JavassistManApi.class.getName() + atomicInteger.incrementAndGet(),classPool.get(Object.class.getName()));
//
//        Class<?>[] interfaces = new Class[2];
//        interfaces[0] = EchoService.class;
//        interfaces[1] = Serializable.class;
//        //添加接口
//        for (Class<?> aInterface : interfaces) {
//            ctClass.addInterface(resolve(aInterface));
//        }
//
//        //添加属性
//        ctClass.addField(new CtField(resolve(ObjectInvoker.class),"invoker",ctClass));
//
//        //添加构造器
//        CtConstructor proxyConstructor = new CtConstructor(resolve(new Class[]{ObjectInvoker.class}),ctClass);
//        //添加构造器方法体
//        proxyConstructor.setBody("{\n\tthis.invoker = $1;}");
//        ctClass.addConstructor(proxyConstructor);
//
//        CtMethod  getMethod =
//                new CtMethod(resolve(Method.class),"_javassistGetMethod"
//                        ,resolve(new Class[]{String.class,String.class,Class[].class}),ctClass);
//        String getMethodBody = "try{\n" +
//                                "return com.github.thushear.proxy.javassist.JavassistManApi.getMethodCache($1,$2,$3);\n" +
//                                "} catch(Exception e){\n" +
//                                "throw new RuntimeException(\"unable to look up method,\",e);\n" +
//                                "}";
//
//        getMethod.setBody(getMethodBody);
//        ctClass.addMethod(getMethod);
//
//        //add hashcode Method
//        CtMethod hashCodeMethod = new CtMethod(resolve(Integer.TYPE),"hashCode",new CtClass[0],ctClass);
//        hashCodeMethod.setBody("{\n\treturn System.identityHashCode(this);\n}");
//        ctClass.addMethod(hashCodeMethod);
//
//        // add equals method
//        CtMethod equalsMethod = new CtMethod(resolve(Boolean.TYPE),"equals",
//                resolve(new Class[]{Object.class}),ctClass);
//        String equalsBody = "{\n\treturn this==$1;\n}";
//        equalsMethod.setBody(equalsBody);
//        ctClass.addMethod(equalsMethod);
//
//        // add implementation methods
//        Method[] methods = EchoService.class.getMethods();
//        for (Method method : methods) {
//            CtMethod ctMethod = new CtMethod(resolve(method.getReturnType())
//                    ,method.getName(),resolve(method.getParameterTypes()),ctClass);
//            String implementMethodBody = "{\n\t return ($r) invoker.invoke(this, "+ "_javassistGetMethod" +"(\""+method.getDeclaringClass().getName() +"\",\""
//                    + method.getName() + "\",$sig),$args);\n" +"}";
//            ctMethod.setBody(implementMethodBody);
//            ctClass.addMethod(ctMethod);
//        }
//
//        ObjectInvoker fixedStringObjInvoker = new ObjectInvoker() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object... arguments) throws Throwable {
//                System.out.println("args = " + arguments);
//                return "hello world";
//            }
//        };
//
//        EchoService echoService = (EchoService) ctClass.toClass().getConstructor(ObjectInvoker.class).newInstance(fixedStringObjInvoker);
//
//        echoService.echo("hello ");
//
//        ctClass.writeFile(JavassistManApi.class.getResource("/").getPath());
//
//    }
//
//    private static final Map<String, Method> methodCache = Maps.newHashMap();
//
//    public static Method getMethodCache(String className,String methodName,Class<?>[] parameterTypes) throws ClassNotFoundException, NoSuchMethodException {
//        String methodSignature = MethodSignature.getName(className,methodName,parameterTypes);
//        Method method = methodCache.get(methodSignature);
//        if (method == null) {
//            method = Class.forName(className).getMethod(methodName,parameterTypes);
//            methodCache.put(methodSignature,method);
//            return method;
//        }
//        return method;
//    }
//
//
//
//
//    private static Set<ClassLoader> classLoaders = Sets.newHashSet();
//
//
//    public static CtClass[] resolve(Class<?>[] classes){
//        CtClass[] ctClasses = new CtClass[classes.length];
//        for (int i = 0; i < classes.length; i++) {
//            ctClasses[i] = resolve(classes[i]);
//        }
//        return ctClasses;
//    }
//
//    public static CtClass resolve(Class<?> clazz){
//        synchronized (classLoaders){
//            ClassLoader classLoader = clazz.getClassLoader();
//            if (classLoader != null && !classLoaders.contains(classLoader)){
//                classLoaders.add(classLoader);
//                classPool.appendClassPath(new LoaderClassPath(classLoader));
//            }
//            try {
//                return classPool.get(getJavaClassName(clazz));
//            } catch (NotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//    }
//
//    public static String getJavaClassName(Class<?> clazz){
//        if (clazz.isArray()){
//            return getJavaClassName(clazz.getComponentType()) + "[]";
//        }
//        return clazz.getName();
//    }
//
//
//
//    /**]
//     * Introspection and customization
//     */
//
//    public static void testMethod() throws NotFoundException, CannotCompileException, IOException {
//
//        ClassPool pool = ClassPool.getDefault();
//        CtClass cc = pool.get("com.github.thushear.proxy.javassist.Point");
//        CtMethod ctMethod = cc.getDeclaredMethod("move");
//        ctMethod.insertBefore("{System.out.println($1);System.out.println($2);}");
//        cc.writeFile(JavassistManApi.class.getResource("/").getPath());
//    }
//
//
//    /**
//     *  Class loader
//     */
//
//    public static void testClassLoader() throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, ClassNotFoundException, IOException {
////        JavassistMock javassistMockTest = new JavassistMock(); error
//        ClassPool pool = ClassPool.getDefault();
//        CtClass ctClass = pool.get("com.github.thushear.proxy.javassist.JavassistMock");
//        CtMethod ctMethod = ctClass.getDeclaredMethod("say");
//        ctMethod.insertBefore("{System.out.println(\"JavassistMock.say()\");}");
//        Class c  = ctClass.toClass();
//        JavassistMock javassistMock = (JavassistMock) c.newInstance();
//        javassistMock.say();
//
//        //Using javassist.Loader
//
//        Loader loader = new Loader(pool);
////        loader.addTranslator(pool,new MyTranslator());
//        Class ctClassLoad =  loader.loadClass("com.github.thushear.proxy.javassist.JavassistMock");
//        ctClassLoad.newInstance();
//
//        //Modifying a system class
//
//        CtClass cc = pool.get("java.lang.String");
//        CtField f = new CtField(CtClass.intType, "hiddenValue", cc);
//        f.setModifiers(Modifier.PUBLIC);
//        cc.addField(f);
//        cc.writeFile(JavassistManApi.class.getResource("/").getPath());
//    }
//
//
//
//    /**
//     * Tut 01  Reading and writing bytecode
//     */
//    public static void testReadAndWriteByteCode() throws NotFoundException, CannotCompileException, IOException, IllegalAccessException, InstantiationException {
//
//        ClassPool pool = ClassPool.getDefault();
//        CtClass ctClass = pool.get("com.github.thushear.proxy.javassist.JavassistManApi");
//        ctClass.setSuperclass(pool.get("com.github.thushear.proxy.javassist.JavassistMock"));
//        // 复制方法 向字节码动态添加方法
//        CtMethod fMethod = ctClass.getDeclaredMethod("f");
//        CtMethod gMethod = CtNewMethod.copy(fMethod,"g",ctClass,null);
//        ctClass.addMethod(gMethod);
//
//
//
//        //Defining a new class
//        CtClass ctClassPoint = pool.makeClass("Point");
//
//
//        System.out.println("byte code:" + Arrays.toString(ctClass.toBytecode()));
//        ctClass.writeFile(JavassistManApi.class.getResource("/").getFile());
//        ctClassPoint.writeFile(JavassistManApi.class.getResource("/").getPath());
//    }
//
//
//    public int f(int i){
//        return i + i;
//    }
//
//}
//
//class  MyTranslator implements Translator{
//
//    @Override
//    public void start(ClassPool pool) throws NotFoundException, CannotCompileException {
//
//    }
//
//    @Override
//    public void onLoad(ClassPool pool, String classname) throws NotFoundException, CannotCompileException {
//        CtClass cc = pool.get(classname);
//        cc.setModifiers(Modifier.PUBLIC);
//    }
//}
//
//class Point {
//    int x, y;
//    void move(int dx, int dy) { x += dx; y += dy; }
//}
//
//class MethodSignature{
//
//
//    public static String getName(String className,String methodName,Class<?>[] paramterTypes){
//        StringBuilder buf = new StringBuilder(className).append(".").append(methodName).append("(");
//        if (!ArrayUtils.isEmpty(paramterTypes)){
//            for (Class<?> paramterType : paramterTypes) {
//                buf.append(paramterType.getName()).append(",");
//            }
//        }
//        buf.append(")");
//        return buf.toString();
//    }
//
//
//}