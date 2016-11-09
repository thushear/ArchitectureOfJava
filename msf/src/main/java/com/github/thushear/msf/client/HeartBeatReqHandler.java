package com.github.thushear.msf.client;

import com.github.thushear.msf.struct.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by kongming on 2016/4/6.
 */
public class HeartBeatReqHandler extends ChannelInboundHandlerAdapter
{

    private volatile ScheduledFuture<?> heartBeat;


    private class HeartBeatTask implements Runnable {

        private final ChannelHandlerContext ctx;

        public HeartBeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            HeartBeatMessage heartBeat = buildHeartBeat();
            ctx.writeAndFlush(heartBeat);
        }

        private HeartBeatMessage buildHeartBeat(){
            HeartBeatMessage nettyMessage = new HeartBeatMessage();
            MessageHeader header = new MessageHeader();
            header.setType(MessageType.HEARTBEAT_REQ.value());
            nettyMessage.setHeader(header);
            return nettyMessage;
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BaseMessage nettyMessage = (BaseMessage) msg;

        if (nettyMessage.getHeader() != null && nettyMessage.getHeader().getType() == MessageType.LOGIN_RESP.value()){
            heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx),0,5000, TimeUnit.MILLISECONDS);
        }else if (nettyMessage.getHeader()!=null && nettyMessage.getHeader().getType() == MessageType.HEARTBEAT_RESP.value()){
            System.out.println("client receive server heart beat message :->"  + nettyMessage);
//            ctx.writeAndFlush(MockMessage.buildRequestMessage());
        }else if (nettyMessage.getHeader() != null && nettyMessage.getHeader().getType() == MessageType.SERVICE_RESP.value()){
            System.out.println("client receive response message :->" + nettyMessage);
            ctx.fireChannelRead(nettyMessage);
        }
        else {
            ctx.fireChannelRead(msg);
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (heartBeat != null){
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }



}
