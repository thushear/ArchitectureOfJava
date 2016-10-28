package com.github.thushear.msf.transport;

import com.github.thushear.msf.codec.MsfMessageDecoder;
import com.github.thushear.msf.codec.MsfMessageEncoder;
import com.github.thushear.msf.server.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.io.IOException;

/**
 * Created by kongming on 2016/10/14.
 */
public class NettyTransporter {




  public static void startNetty(String bindAddress,int port,ServerHandler serverHandler) throws InterruptedException {

    // 配置服务端的NIO线程组
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    ServerBootstrap b = new ServerBootstrap();
    b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 100)
            .handler(new LoggingHandler(LogLevel.INFO))
            .childHandler(new ChannelInitializer<SocketChannel>() {
              @Override
              public void initChannel(SocketChannel ch)
                      throws IOException {
                ch.pipeline().addLast(
                        new MsfMessageDecoder(1024 * 1024, 4, 4));
                ch.pipeline().addLast(new MsfMessageEncoder());
                ch.pipeline().addLast("readTimeoutHandler",
                        new ReadTimeoutHandler(50));
                ch.pipeline().addLast(new LoginAuthRespHandler());
                ch.pipeline().addLast("HeartBeatHandler",
                        new HeartBeatRespHandler())
                .addLast("serverHandler",serverHandler);
              }
            });

    // 绑定端口，同步等待成功
    b.bind(bindAddress, port).sync();

  }

}
