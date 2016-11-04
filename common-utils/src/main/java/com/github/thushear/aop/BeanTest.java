package com.github.thushear.aop;


import org.springframework.aop.SpringProxy;
import org.springframework.aop.framework.Advised;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * 说明:
 * User: kongming
 * Date: 14-6-5
 * Time: 下午3:05
 */
public class BeanTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field field = System.class.getDeclaredField("props");
        field.setAccessible(true);
        Properties props = (Properties) field.get(null);
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\cglib");//dir为保存文件路径
        props.put("net.sf.cglib.core.DebuggingClassWriter.traceEnabled", "true");
        ApplicationContext context = new ClassPathXmlApplicationContext("aopbeans.xml");
        //Chinese c = context.getBean("chinese",Chinese.class);
        AopClassSample aopClassSample = context.getBean("aopClassSample",AopClassSample.class);
        aopClassSample.testAop("ss");
        //System.out.println(c.sayHello("kongm aop"));
        Person p  = context.getBean("chinese",Person.class);


        ProxyGenerator.generateProxyClass("net.shopin.aop.Person$Proxy",new Class[]{p.getClass()});
        System.out.println(p.sayHello("interface aop"));
        System.out.println(p.getClass());
        //生成代理类实际调用的方法 可以查看生成的代理类
        generateProxyClassFile(Person.class.getName() + "$Proxy.class", new Class[]{Person.class,SpringProxy.class,Advised.class}, Person.class.getResource("/").getPath());

    }

    public static final String FILE_SEPERATOR = File.separator;
    public static void generateProxyClassFile(String proxyFileName, Class[] interfaces, String filePath) {

        byte[] proxyClassBytes = ProxyGenerator.generateProxyClass(proxyFileName, interfaces);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(filePath + FILE_SEPERATOR + proxyFileName));
            fileOutputStream.write(proxyClassBytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

    }
}
