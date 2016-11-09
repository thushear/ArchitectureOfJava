package com.github.thushear.msf.consumer;

import com.github.thushear.msf.client.ClientFactory;
import com.github.thushear.msf.client.NettyClient;
import com.github.thushear.msf.util.ClassLoaderUtils;
import com.github.thushear.msf.util.ProxyFactory;
import io.netty.channel.Channel;

/**
 * Created by kongming on 2016/11/7.
 */
public class Consumer<T> {


    private transient volatile T proxyIns;


    private String interfaceId;

    public Consumer(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public synchronized T refer(){
        if (proxyIns != null){
            return proxyIns;
        }

        try {
            Channel channel = ClientFactory.getNettyClient();
            ConsumerInvoker consumerInvoker = new ConsumerInvoker(channel,this);
            Class   interfaceClazz = ClassLoaderUtils.forName(interfaceId);
            proxyIns = (T) ProxyFactory.buildProxy(interfaceClazz,consumerInvoker);
            return proxyIns;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }



}
