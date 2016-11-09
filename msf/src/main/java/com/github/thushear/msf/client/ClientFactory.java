package com.github.thushear.msf.client;

import io.netty.channel.Channel;

/**
 * Created by kongming on 2016/11/7.
 */
public final class ClientFactory {





    public static Channel getNettyClient() throws Exception {
        //TODO  先固定8080
        return new NettyClient().connect(8080,"127.0.0.1");
    }


}
