package com.github.thushear.proxy.bytebuddy;

import com.github.thushear.proxy.ObjectInvokerAccessor;
import com.github.thushear.test.EchoService;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;

import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;
import static net.bytebuddy.matcher.ElementMatchers.not;
/**
 * Created by kongming on 2016/11/3.
 */
public class ByteBuddyManApi {


    public static void main(String[] args) {


    }



    public static void testCreateInvoker(){
        Class dynamicUserType = new ByteBuddy().subclass(EchoService.class)
                .implement(ObjectInvokerAccessor.class)
                .intercept(FieldAccessor.ofBeanProperty())
                .method(not(isDeclaredBy(Object.class)))
                .intercept(MethodDelegation.to(DynamicDelegator.class))
                .make()
                .load( ByteBuddyManApi.class.getClassLoader() )
                .getLoaded();




    }
}
