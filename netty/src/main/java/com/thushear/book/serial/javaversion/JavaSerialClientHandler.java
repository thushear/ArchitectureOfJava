package com.thushear.book.serial.javaversion;

import com.thushear.book.serial.SubsrcibeReq;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by kongming on 2016/3/18.
 */
public class JavaSerialClientHandler extends ChannelInboundHandlerAdapter {


    private SubsrcibeReq buildReq(int i){
        SubsrcibeReq req = new SubsrcibeReq( );
        req.setId(i);
        req.setName("thushear");
        req.setPhone("123456789");
        return  req;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //测试粘包和拆包
        for (int i = 0; i < 10; i++) {
            ctx.write(buildReq(i));
        }
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("receive msg :" + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
