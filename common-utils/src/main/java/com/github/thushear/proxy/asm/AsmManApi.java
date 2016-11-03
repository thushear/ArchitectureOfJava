package com.github.thushear.proxy.asm;

import com.github.thushear.proxy.ObjectInvoker;
import com.github.thushear.test.EchoService;


import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kongming on 2016/11/3.
 */
public class AsmManApi {


    public static void main(String[] args) {
        testAsmApi();
    }
    private static final AtomicInteger CLASS_NUMBER = new AtomicInteger(0);

    private static final String CLASSNAME_PREFIX = "CommonsProxyASM_";
    private static final String HANDLER_NAME = "__handler";
    private static final Type INVOKER_TYPE = Type.getType(ObjectInvoker.class);
    public static void testAsmApi(){

        String proxyName = CLASSNAME_PREFIX + CLASS_NUMBER.incrementAndGet();
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        String classFileName = proxyName.replace('.', '/');
        Type proxyType = Type.getObjectType(classFileName);

        Type superType = Type.getType(Object.class);
        String[] interfacesname = new String[]{EchoService.class.getName()};
        classWriter.visit(Opcodes.V1_6,Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER,proxyType.getInternalName(),null,superType.getInternalName(),
                interfacesname);
        // create Invoker field
        classWriter.visitField(Opcodes.ACC_FINAL + Opcodes.ACC_PRIVATE, HANDLER_NAME, INVOKER_TYPE.getDescriptor(), null, null).visitEnd();

        init(classWriter,proxyType,superType);

        Method[] methods = EchoService.class.getMethods();
        for (Method method : methods) {
            processMethod(classWriter, method, proxyType, HANDLER_NAME);
        }

        byte[] bytes = classWriter.toByteArray();

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File("D:\\" +  "asm.class"));
            fileOutputStream.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw  new RuntimeException(e);
            }
        }

    }
    private static void init(ClassWriter cw, Type proxyType, Type superType) {
        GeneratorAdapter mg =
                new GeneratorAdapter(Opcodes.ACC_PUBLIC, new org.objectweb.asm.commons.Method("<init>", Type.VOID_TYPE,
                        new Type[] {INVOKER_TYPE}), null, null, cw);
        // invoke super constructor:
        mg.loadThis();
        mg.invokeConstructor(superType, org.objectweb.asm.commons.Method.getMethod("void <init> ()"));

        // assign handler:
        mg.loadThis();
        mg.loadArg(0);
        mg.putField(proxyType, HANDLER_NAME, INVOKER_TYPE);
        mg.returnValue();
        mg.endMethod();
    }

    private static Type[] getTypes(Class<?>... src) {
        Type[] result = new Type[src.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Type.getType(src[i]);
        }

        return result;
    }



    // FIXME 虽然使用ASM，但是利用了反射，性能不佳
    private static void processMethod(ClassWriter cw, Method method, Type proxyType, String handlerName)
             {
        // Type sig = Type.getType(method);
        Type[] exceptionTypes = getTypes(method.getExceptionTypes());

        // push the method definition
        int access = (Opcodes.ACC_PUBLIC | Opcodes.ACC_PROTECTED) & method.getModifiers();
        org.objectweb.asm.commons.Method m = org.objectweb.asm.commons.Method.getMethod(method);
        GeneratorAdapter mg = new GeneratorAdapter(access, m, null, getTypes(method.getExceptionTypes()), cw);

        Label tryBlock = exceptionTypes.length > 0 ? mg.mark() : null;

        mg.push(Type.getType(method.getDeclaringClass()));

        // the following code generates the bytecode for this line of Java:
        // Method method = <proxy>.class.getMethod("add", new Class[] {
        // <array of function argument classes> });

        // get the method name to invoke, and push to stack

        mg.push(method.getName());

        // create the Class[]
        mg.push(Type.getArgumentTypes(method).length);
        Type classType = Type.getType(Class.class);
        mg.newArray(classType);

        // push parameters into array
        for (int i = 0; i < Type.getArgumentTypes(method).length; i++) {
            // keep copy of array on stack
            mg.dup();

            // push index onto stack
            mg.push(i);
            mg.push(Type.getArgumentTypes(method)[i]);
            mg.arrayStore(classType);
        }

        // invoke getMethod() with the method name and the array of types
        mg.invokeVirtual(classType, org.objectweb.asm.commons.Method
                .getMethod("java.lang.reflect.Method getDeclaredMethod(String, Class[])"));
        // store the returned method for later

        // the following code generates bytecode equivalent to:
        // return ((<returntype>) invoker.invoke(this, method, new Object[]
        // { <function arguments }))[.<primitive>Value()];

        mg.loadThis();

        mg.getField(proxyType, handlerName, INVOKER_TYPE);
        // put below method:
        mg.swap();

        // we want to pass "this" in as the first parameter
        mg.loadThis();
        // put below method:
        mg.swap();

        // need to construct the array of objects passed in

        // create the Object[]
        mg.push(Type.getArgumentTypes(method).length);
        Type objectType = Type.getType(Object.class);
        mg.newArray(objectType);

        // push parameters into array
        for (int i = 0; i < Type.getArgumentTypes(method).length; i++) {
            // keep copy of array on stack
            mg.dup();

            // push index onto stack
            mg.push(i);

            mg.loadArg(i);
            mg.valueOf(Type.getArgumentTypes(method)[i]);
            mg.arrayStore(objectType);
        }

        // invoke the invoker
        mg.invokeInterface(INVOKER_TYPE, org.objectweb.asm.commons.Method
                .getMethod("Object invoke(Object, java.lang.reflect.Method, Object[])"));

        // cast the result
        mg.unbox(Type.getReturnType(method));

        // push return
        mg.returnValue();

        // catch InvocationTargetException
        if (exceptionTypes.length > 0) {
            Type caughtExceptionType = Type.getType(InvocationTargetException.class);
            mg.catchException(tryBlock, mg.mark(), caughtExceptionType);

            Label throwCause = new Label();

            mg.invokeVirtual(caughtExceptionType,
                    org.objectweb.asm.commons.Method.getMethod("Throwable getCause()"));

            for (int i = 0; i < exceptionTypes.length; i++) {
                mg.dup();
                mg.push(exceptionTypes[i]);
                mg.swap();
                mg.invokeVirtual(classType,
                        org.objectweb.asm.commons.Method.getMethod("boolean isInstance(Object)"));
                // if true, throw cause:
                mg.ifZCmp(GeneratorAdapter.NE, throwCause);
            }
            // no exception types matched; throw
            // UndeclaredThrowableException:
            int cause = mg.newLocal(Type.getType(Exception.class));
            mg.storeLocal(cause);
            Type undeclaredType = Type.getType(UndeclaredThrowableException.class);
            mg.newInstance(undeclaredType);
            mg.dup();
            mg.loadLocal(cause);
            mg.invokeConstructor(undeclaredType, new org.objectweb.asm.commons.Method("<init>", Type.VOID_TYPE,
                    new Type[] {Type.getType(Throwable.class)}));
            mg.throwException();

            mg.mark(throwCause);
            mg.throwException();
        }

        // finish this method
        mg.endMethod();
    }


}
