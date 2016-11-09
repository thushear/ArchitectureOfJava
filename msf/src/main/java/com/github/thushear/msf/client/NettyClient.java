package com.github.thushear.msf.client;

import com.github.thushear.msf.codec.MsfMessageDecoder;
import com.github.thushear.msf.codec.MsfMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by kongming on 2016/4/6.
 */
public class NettyClient {


    private ScheduledExecutorService executor = Executors
            .newScheduledThreadPool(1);

    EventLoopGroup group = new NioEventLoopGroup();

    public Channel connect(int port, String host) throws Exception {
        Channel channel = null;
        // 配置客户端NIO线程组
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(
                                    new MsfMessageDecoder(1024 * 1024, 4, 4));
                            ch.pipeline().addLast("MessageEncoder",
                                    new MsfMessageEncoder());
                            ch.pipeline().addLast("readTimeoutHandler",
                                    new ReadTimeoutHandler(50));
                            ch.pipeline().addLast("LoginAuthHandler",
                                    new LoginAuthReqHandler());
                            ch.pipeline().addLast("HeartBeatHandler",
                                    new HeartBeatReqHandler());
                            ch.pipeline().addLast("clientChannelHandler",new ClientChannelHandler());
                        }
                    });

//            ChannelFuture channelFuture = b.connect(host,port);

            // 发起异步连接操作
            ChannelFuture channelFuture = b.connect(
                    new InetSocketAddress(host, port),
                    new InetSocketAddress("127.0.0.1",
                            8081)).sync();
            // 当对应的channel关闭的时候，就会返回对应的channel。
            // Returns the ChannelFuture which will be notified when this channel is closed. This method always returns the same future instance.
            channelFuture.awaitUninterruptibly(2000,TimeUnit.MILLISECONDS);
            if (channelFuture.isSuccess()){
                channel = channelFuture.channel();
            }
            return channel;
        } finally {
            // 所有资源释放完成之后，清空资源，再次发起重连操作
//            executor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                        try {
//                            connect(8080, "127.0.0.1");// 发起重连操作
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
        }


    }



}
