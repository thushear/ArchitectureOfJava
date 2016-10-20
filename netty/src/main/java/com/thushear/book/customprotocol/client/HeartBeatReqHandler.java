package com.thushear.book.customprotocol.client;

import com.thushear.book.customprotocol.MessageType;
import com.thushear.book.customprotocol.struct.Header;
import com.thushear.book.customprotocol.struct.NettyMessage;
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
            NettyMessage heartBeat = buildHeartBeat();
            ctx.writeAndFlush(heartBeat);
        }

        private NettyMessage buildHeartBeat(){
            NettyMessage nettyMessage = new NettyMessage();
            Header header = new Header();
            header.setType(MessageType.HEARTBEAT_REQ.value());
            nettyMessage.setHeader(header);
            return nettyMessage;
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage nettyMessage = (NettyMessage) msg;

        if (nettyMessage.getHeader() != null && nettyMessage.getHeader().getType() == MessageType.LOGIN_RESP.value()){
            heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx),0,5000, TimeUnit.MILLISECONDS);
        }else if (nettyMessage.getHeader()!=null && nettyMessage.getHeader().getType() == MessageType.HEARTBEAT_RESP.value()){
            System.out.println("client receive server heart beat message :->"  + nettyMessage);
        }else {
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
