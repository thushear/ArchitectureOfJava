package com.github.thushear.msf.client;

import com.github.thushear.msf.consumer.MsgFuture;
import com.github.thushear.msf.struct.MessageType;
import com.github.thushear.msf.struct.ResponseMessage;
import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;

/**
 * Created by kongming on 2016/11/9.
 */
public class ClientChannelHandler extends ChannelInboundHandlerAdapter {

    private static final Map<Long,MsgFuture> msgFutureMap = Maps.newConcurrentMap();


    public static void registerMsg(Long sessionId,MsgFuture msgFuture){
        msgFutureMap.putIfAbsent(sessionId,msgFuture);
    }



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();

        if (msg instanceof ResponseMessage ){
            ResponseMessage responseMessage = (ResponseMessage) msg;
            if (responseMessage.getHeader().getType() == MessageType.SERVICE_RESP.value()){
                long sessionId = responseMessage.getHeader().getSessionId();
                MsgFuture msgFuture = msgFutureMap.get(sessionId);
                if (msgFuture != null) {
                    msgFuture.setSuccess(msg);

                }
                msgFutureMap.remove(sessionId);
            }


        }

    }
}
