package com.github.thushear.msf.client;

import com.github.thushear.msf.struct.BaseMessage;
import com.github.thushear.msf.struct.HandShakeMessage;
import com.github.thushear.msf.struct.MessageHeader;
import com.github.thushear.msf.struct.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by kongming on 2016/4/6.
 */
public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buildLoginReq());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        BaseMessage message  = (BaseMessage) msg;

        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.value() && message instanceof HandShakeMessage){

            byte loginResult = (byte) ((HandShakeMessage)message).getResult();
            if (loginResult != (byte)0){
                ctx.close();
            }else {
                ctx.fireChannelRead(msg);
            }
        }else {
            ctx.fireChannelRead(msg);
        }

    }



    private HandShakeMessage buildLoginReq(){
        HandShakeMessage message = new HandShakeMessage();
        MessageHeader header = new MessageHeader();
        header.setType(MessageType.LOGIN_REQ.value());
        message.setHeader(header);
        return message;

    }
}
