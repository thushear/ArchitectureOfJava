package com.thushear.book.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by kongming on 2016/3/17.
 */
public class DelimeEchoServerHandler extends ChannelInboundHandlerAdapter {

    private int counter = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("this is " + ++counter + " the time receive " + body);
        body += "$_";
        ByteBuf echoBuffer = Unpooled.copiedBuffer(body.getBytes());
        ctx.writeAndFlush(echoBuffer);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
