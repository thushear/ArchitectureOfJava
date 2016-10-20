package com.thushear.book.serial.javaversion;

import com.thushear.book.serial.SubscribeResp;
import com.thushear.book.serial.SubsrcibeReq;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by kongming on 2016/3/18.
 */
public class SubReqHandler extends ChannelInboundHandlerAdapter {

    private int counter ;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("receive msg : " + msg + "  the couner "  + ++counter);
        SubsrcibeReq req = (SubsrcibeReq) msg;
        if ("thushear".equalsIgnoreCase(((SubsrcibeReq) msg).getName())){
            SubscribeResp resp =  new SubscribeResp();
            resp.setCode(1);
            resp.setDesc("success");
            resp.setReqId(1);
            ctx.writeAndFlush(resp);
        }

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
