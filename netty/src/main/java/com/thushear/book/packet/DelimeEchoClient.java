package com.thushear.book.packet;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by kongming on 2016/3/17.
 */
public class DelimeEchoClient {

    public static void main(String[] args) throws InterruptedException {
        new DelimeEchoClient().connect(8080,"127.0.0.1");
    }

    public void connect(int port,String host) throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ByteBuf delimeter = Unpooled.copiedBuffer("$_".getBytes());
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimeter))
                                    .addLast(new StringDecoder())
                                    .addLast(new DelimeEchoClientHandler());
                        }
                    });

            ChannelFuture f = b.connect(host,port).sync();
            f.channel().closeFuture().sync();
        } finally {

            group.shutdownGracefully();
        }
    }

}
