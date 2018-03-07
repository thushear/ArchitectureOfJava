package com.github.thushear.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by kongming on 2017/12/27.
 */
public class JDK8HeapTest {

    /**
     *   -verbose -verbose:gc -Xms128m -Xmx128m -Xloggc:D://gc.log -XX:+PrintGCApplicationStoppedTime
     *   -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=72m
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        List list = new ArrayList();
        while (true){

            for (int i = 0; i < 1000; i++) {
                byte[] oneM = new byte[1024 * 1024];
                list.add(oneM);

            }

            TimeUnit.SECONDS.sleep(5);
                   }

    }


}
