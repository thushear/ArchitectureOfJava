package com.github.thushear.proxy.cglib;

import com.github.thushear.proxy.ObjectInvoker;
import com.github.thushear.proxy.ProxyUtils;
import com.github.thushear.test.EchoService;
import com.github.thushear.test.EchoServiceImpl;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Created by kongming on 2016/11/3.
 */
public class CglibManApi {


    public static void main(String[] args) throws Exception {

        Field field = System.class.getDeclaredField("props");
        field.setAccessible(true);
        Properties props = (Properties) field.get(null);
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\cglib");//dir为保存文件路径
        props.put("net.sf.cglib.core.DebuggingClassWriter.traceEnabled", "true");

        testCreateInvokerApi();

//        testEnhancer();
//        testInvocationHandler();

//        testMethodInterceptor();
//        testCallbackFilter();
//        testBeanGenerator();
//        testBeanCopier();
//        testBeanMap();
    }


    //Bean map
    public static void testBeanMap() {
        SampleBean bean = new SampleBean();
        BeanMap beanMap = BeanMap.create(bean);
        System.out.println(beanMap);
        bean.setValue("hello");
        System.out.println(beanMap.get("value"));
        System.out.println(beanMap);
    }


    //Bean copier
    public static void testBeanCopier() {
        BeanCopier copier = BeanCopier.create(SampleBean.class, OtherSampleBean.class, false);
        SampleBean bean = new SampleBean();
        bean.setValue("hello ");
        OtherSampleBean otherSampleBean = new OtherSampleBean();
        copier.copy(bean, otherSampleBean, null);
        System.out.println(otherSampleBean.getValue());
    }


    public static void testBeanGenerator() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        BeanGenerator beanGenerator = new BeanGenerator();
        beanGenerator.addProperty("value", String.class);
        Object mybean = beanGenerator.create();
        Method setter = mybean.getClass().getMethod("setValue", String.class);
        setter.invoke(mybean, "hello cglib");
        Method getter = mybean.getClass().getMethod("getValue");
        Object result = getter.invoke(mybean);
        System.out.println("result = " + result);

    }


    public static void testCallbackFilter() throws Exception {
        Enhancer enhancer = new Enhancer();
        CallbackHelper callbackHelper = new CallbackHelper(SampleClass.class, new Class[0]) {

            @Override
            protected Object getCallback(Method method) {
                if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                    return new FixedValue() {
                        @Override
                        public Object loadObject() throws Exception {
                            return "Hello cglib!";
                        }
                    };
                } else {
                    return NoOp.INSTANCE;
                }

            }
        };
        enhancer.setSuperclass(SampleClass.class);
        enhancer.setCallbackFilter(callbackHelper);
        enhancer.setCallbacks(callbackHelper.getCallbacks());
        SampleClass sampleClass = (SampleClass) enhancer.create();
        System.out.println(sampleClass.test("11"));
        System.out.println(sampleClass.toString());

    }


    public static void testMethodInterceptor() {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleClass.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                    return "hello cglib invoker";
                } else {
                    return methodProxy.invokeSuper(o, objects);
                }
            }
        });
        SampleClass sampleClass = (SampleClass) enhancer.create();
        System.out.println(sampleClass.test("11"));
        System.out.println(sampleClass.toString());

    }


    public static void testInvocationHandler() {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleClass.class);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                    return "hello cglib invoker";
                } else {
                    throw new RuntimeException();
                }

            }
        });

        SampleClass sampleClass = (SampleClass) enhancer.create();
        System.out.println(sampleClass.test("11"));
        System.out.println(sampleClass.toString());

    }


    public static void testEnhancer() {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleClass.class);
        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "hello cglib";
            }
        });
        SampleClass proxy = (SampleClass) enhancer.create();
        String result = proxy.test("hhh");
        System.out.println("result = " + result);

    }


    public static void testCreateInvokerApi() {

        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(Thread.currentThread().getContextClassLoader());

        enhancer.setInterfaces(new Class[]{EchoService.class, Serializable.class});
        enhancer.setSuperclass(Object.class);
        enhancer.setCallbackFilter(new CglibProxyFactoryCallbackFilter());
        enhancer.setCallbacks(new Callback[]{new InvokerBridge(new DelegateInvoker(new EchoServiceImpl())), new EqualsHandler(), new HashCodeHandler()});

        EchoService echoService = (EchoService) enhancer.create();

        String echo = echoService.echo("hello world");
        System.out.println("echo = " + echo);


    }


    private static class DelegateInvoker implements ObjectInvoker {

        private final Object delegate;

        public DelegateInvoker(Object delegate) {
            this.delegate = delegate;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object... arguments) throws Throwable {
            return method.invoke(delegate, arguments);
        }
    }

    private static class InvokerBridge implements InvocationHandler, Serializable {

        private final ObjectInvoker original;

        public InvokerBridge(ObjectInvoker original) {
            this.original = original;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            return original.invoke(o, method, objects);
        }
    }


    private static class EqualsHandler implements MethodInterceptor, Serializable {


        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            return Integer.valueOf(System.identityHashCode(o));
        }
    }

    private static class HashCodeHandler implements MethodInterceptor, Serializable {


        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            return null;
        }
    }


    private static class CglibProxyFactoryCallbackFilter implements CallbackFilter {

        @Override
        public int accept(Method method) {
            if (ProxyUtils.isEqualsMethod(method)) {
                return 1;
            }
            if (ProxyUtils.isHashCode(method)) {
                return 2;
            }
            return 0;
        }
    }


}
