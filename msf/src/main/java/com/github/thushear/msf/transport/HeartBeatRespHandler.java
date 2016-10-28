package com.github.thushear.msf.transport;

import com.github.thushear.msf.struct.BaseMessage;
import com.github.thushear.msf.struct.HeartBeatMessage;
import com.github.thushear.msf.struct.MessageHeader;
import com.github.thushear.msf.struct.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by kongming on 2016/4/6.
 */
@SuppressWarnings("Duplicates")
public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BaseMessage nettyMessage = (BaseMessage) msg;

        if (nettyMessage.getHeader() != null && nettyMessage.getHeader().getType() == MessageType.HEARTBEAT_REQ.value()){
            System.out.println("server receive client heart beat : " + nettyMessage);
            HeartBeatMessage heartBeat =  buildHeartBeat();
            ctx.writeAndFlush(heartBeat);
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    private HeartBeatMessage buildHeartBeat() {
        HeartBeatMessage message = new HeartBeatMessage();
        MessageHeader header = new MessageHeader();
        header.setType(MessageType.HEARTBEAT_RESP.value());
        message.setHeader(header);
        return message;
    }
}
