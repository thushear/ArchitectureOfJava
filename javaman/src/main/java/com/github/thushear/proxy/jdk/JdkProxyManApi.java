package com.github.thushear.proxy.jdk;

import com.github.thushear.test.EchoService;
import com.github.thushear.test.EchoServiceImpl;
import sun.misc.ProxyGenerator;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by kongming on 2016/11/3.
 * <p>
 * <p>
 * 首先是动态生成的代理类本身的一些特点。1）包：如果所代理的接口都是 public 的，那么它将被定义在顶层包（即包路径为空），如果所代理的接口中有非 public 的接口（因为接口不能被定义为 protect 或 private，所以除 public 之外就是默认的 package 访问级别），那么它将被定义在该接口所在包（假设代理了 com.ibm.developerworks 包中的某非 public 接口 A，那么新生成的代理类所在的包就是 com.ibm.developerworks），这样设计的目的是为了最大程度的保证动态代理类不会因为包管理的问题而无法被成功定义并访问；2）类修饰符：该代理类具有 final 和 public 修饰符，意味着它可以被所有的类访问，但是不能被再度继承；3）类名：格式是“$ProxyN”，其中 N 是一个逐一递增的阿拉伯数字，代表 Proxy 类第 N 次生成的动态代理类，值得注意的一点是，并不是每次调用 Proxy 的静态方法创建动态代理类都会使得 N 值增加，原因是如果对同一组接口（包括接口排列的顺序相同）试图重复创建动态代理类，它会很聪明地返回先前已经创建好的代理类的类对象，而不会再尝试去创建一个全新的代理类，这样可以节省不必要的代码重复生成，提高了代理类的创建效率。
 */

/**
 *
 * 首先是动态生成的代理类本身的一些特点。1）包：如果所代理的接口都是 public 的，那么它将被定义在顶层包（即包路径为空），如果所代理的接口中有非 public 的接口（因为接口不能被定义为 protect 或 private，所以除 public 之外就是默认的 package 访问级别），那么它将被定义在该接口所在包（假设代理了 com.ibm.developerworks 包中的某非 public 接口 A，那么新生成的代理类所在的包就是 com.ibm.developerworks），这样设计的目的是为了最大程度的保证动态代理类不会因为包管理的问题而无法被成功定义并访问；2）类修饰符：该代理类具有 final 和 public 修饰符，意味着它可以被所有的类访问，但是不能被再度继承；3）类名：格式是“$ProxyN”，其中 N 是一个逐一递增的阿拉伯数字，代表 Proxy 类第 N 次生成的动态代理类，值得注意的一点是，并不是每次调用 Proxy 的静态方法创建动态代理类都会使得 N 值增加，原因是如果对同一组接口（包括接口排列的顺序相同）试图重复创建动态代理类，它会很聪明地返回先前已经创建好的代理类的类对象，而不会再尝试去创建一个全新的代理类，这样可以节省不必要的代码重复生成，提高了代理类的创建效率。
 */

/**
 * 诚然，Proxy 已经设计得非常优美，但是还是有一点点小小的遗憾之处，那就是它始终无法摆脱仅支持 interface 代理的桎梏，因为它的设计注定了这个遗憾。回想一下那些动态生成的代理类的继承关系图，它们已经注定有一个共同的父类叫 Proxy。Java 的继承机制注定了这些动态代理类们无法实现对 class 的动态代理，原因是多继承在 Java 中本质上就行不通。
 */
public class JdkProxyManApi {


    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");

        testProxyApi();
    }


    public static void testProxyApi() {

        EchoService echoService = (EchoService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{EchoService.class, Serializable.class}, new DynamicProxyInvokerHandler(new EchoServiceImpl()));
        String result = echoService.echo("hello");
        System.out.println("result = " + result);
        //生成代理类实际调用的方法 可以查看生成的代理类
        generateProxyClassFile(JdkProxyManApi.class.getName() + "$Proxy.class", new Class[]{PackageEchoService.class}, JdkProxyManApi.class.getResource("/").getPath());

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

class DynamicProxyInvokerHandler implements InvocationHandler {


    private Object delegateImpl;

    public DynamicProxyInvokerHandler(Object realObj) {
        this.delegateImpl = realObj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return method.invoke(delegateImpl, args);
    }
}

interface PackageEchoService{

    String echo(String echo);
}