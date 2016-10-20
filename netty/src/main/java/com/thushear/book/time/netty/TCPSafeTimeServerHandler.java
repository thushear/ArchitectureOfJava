package com.thushear.book.time.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by kongming on 2016/3/17.
 */

/**
 * 处理了粘包和拆包问题的处理器
 */
public class TCPSafeTimeServerHandler extends ChannelInboundHandlerAdapter
{

    private int counter ;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("the time server receive :" + body + " the couter is :" +  ++counter);
        String current = "query current time".equalsIgnoreCase(body) ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "bad";
        current = current + System.getProperty("line.separator");
        ByteBuf byteBuf = Unpooled.copiedBuffer(current.getBytes());
        ctx.writeAndFlush(byteBuf);



    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
