package com.github.thushear.msf.util;

import com.github.thushear.msf.consumer.ConsumerInvoker;
import com.github.thushear.msf.struct.RequestMessage;
import com.github.thushear.msf.struct.ResponseMessage;
import javassist.*;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kongming on 2016/11/7.
 */
public  final class ProxyFactory {

    private static AtomicInteger counter = new AtomicInteger();

    public static <T> T buildProxy(Class<T> clazz, ConsumerInvoker consumerInvoker) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        Object instance = null;
        try {
            String interfaceName = ClassLoaderUtils.getTypeStr(clazz);
            ClassPool pool = ClassPool.getDefault();
            pool.appendClassPath(new LoaderClassPath(ClassLoaderUtils.getClassLoader(ProxyFactory.class)));
            CtClass  ctClass = pool.makeClass(interfaceName + "_proxy" + counter.incrementAndGet());
            ctClass.addInterface(pool.get(interfaceName));
            ctClass.addField(CtField.make("public " +  ConsumerInvoker.class.getCanonicalName() +  "  invoker = null;",ctClass ));
            List<String> methodList = createMethod(clazz,pool);
            for (String methodStr : methodList) {
                ctClass.addMethod(CtMethod.make(methodStr,ctClass));
            }

            Class proxyClazz = ctClass.toClass();
            instance = proxyClazz.newInstance();
            proxyClazz.getField("invoker").set(instance,consumerInvoker);

        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return (T)instance;

    }


    private static List<String> createMethod(Class<?> interfaceClass,ClassPool pool){
        Method[] methods = interfaceClass.getMethods();
        StringBuilder sb = new StringBuilder();
        List<String> resultList = new ArrayList<String>();
        for (Method method : methods) {
            Class<?>[] mType = method.getParameterTypes();
            Class<?> returnType = method.getReturnType();
            sb.append(java.lang.reflect.Modifier.toString(method.getModifiers()).replace("abstract","") + "  " +
                 ClassLoaderUtils.getName(returnType) + "  " + method.getName() + "(" );
            int c = 0;
            for (Class<?> aClass : mType) {
                sb.append(" " + aClass.getCanonicalName() + " arg" + c + ",");
                c++;
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            Class[] exceptions = method.getExceptionTypes();
            if (exceptions.length > 0 ){
                sb.append(" throw ");
                for (Class exception : exceptions) {
                    sb.append(exception.getCanonicalName() + " , ");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("{\n ");
            sb.append(" Class clazz = " + interfaceClass.getCanonicalName() + ".class;\n");
            sb.append(" String methodName = \"" + method.getName() + "\";\n");
            sb.append(" Class[] paramTypes = new Class[" + c + "];\n" );
            sb.append(" Object[] paramValues = new Object[" + c + "];\n");
            for (int i = 0; i < c; i++) {
                sb.append("paramValues[" + i +"]= ($w)$" + (i + 1) + ";\n");
                sb.append("paramTypes[" + i + "] = " + mType[i].getCanonicalName() + ".class;\n");
            }

            sb.append(RequestMessage.class.getCanonicalName() + " requestMessage = " +
                    MessageBuilder.class.getCanonicalName() + ".buildRequest(clazz,methodName, paramTypes, paramValues);\n");
            sb.append(ResponseMessage.class.getCanonicalName() + " responseMessage = " + "invoker.invoke(requestMessage);\n" );
            sb.append("if(responseMessage.isError()){\nthrow responseMessage.getException();\n}").append("\n");
            if (returnType.equals(void.class)) {
                sb.append(" return; ");
            }else {
                sb.append(" return ").append(asArgument( returnType, "responseMessage.getResponse();"));
            }
            sb.append("\n}");
            resultList.add(sb.toString());
            sb.delete(0,sb.length());

        }
        return resultList;
    }

    private static String asArgument(Class<?> cl, String name)	{
        if( cl.isPrimitive() )	{
            if( Boolean.TYPE == cl )
                return name + "==null?false:((Boolean)" + name + ").booleanValue()";
            if( Byte.TYPE == cl )
                return name + "==null?(byte)0:((Byte)" + name + ").byteValue()";
            if( Character.TYPE == cl )
                return name + "==null?(char)0:((Character)" + name + ").charValue()";
            if( Double.TYPE == cl )
                return name + "==null?(double)0:((Double)" + name + ").doubleValue()";
            if( Float.TYPE == cl )
                return name + "==null?(float)0:((Float)" + name + ").floatValue()";
            if( Integer.TYPE == cl )
                return name + "==null?(int)0:((Integer)" + name + ").intValue()";
            if( Long.TYPE == cl )
                return name + "==null?(long)0:((Long)" + name + ").longValue()";
            if( Short.TYPE == cl )
                return name + "==null?(short)0:((Short)" + name + ").shortValue()";
            throw new RuntimeException(name+" is unknown primitive type.");
        }
        return "(" + ClassLoaderUtils.getName(cl) + ")"+name;
    }
}
