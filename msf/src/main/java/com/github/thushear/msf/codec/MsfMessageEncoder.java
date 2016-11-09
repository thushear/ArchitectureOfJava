package com.github.thushear.msf.codec;

import com.github.thushear.msf.struct.*;
import com.github.thushear.msf.util.MsfConstants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;
import java.util.Map;

/**
 * Created by kongming on 2016/10/28.
 */
public class MsfMessageEncoder extends MessageToByteEncoder<BaseMessage> {

    MarshallingEncoder marshallingEncoder;

    public MsfMessageEncoder() throws IOException {
        this.marshallingEncoder = new MarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, BaseMessage msg, ByteBuf out) throws Exception {

        if (msg == null || msg.getHeader() == null)
            return;

        out.writeInt(msg.getHeader().getMagicCode());
        out.writeInt(msg.getHeader().getLength());
        out.writeLong(msg.getHeader().getSessionId());
        out.writeByte(msg.getHeader().getType());
        out.writeByte(msg.getHeader().getPriority());
        out.writeInt(msg.getHeader().getAttachment().size());

        String key = null;
        byte[] keyByteArray = null;
        Object value = null;
        for (Map.Entry<String, Object> entry : msg.getHeader().getAttachment().entrySet()) {
             key = entry.getKey();
             keyByteArray = key.getBytes(MsfConstants.DEFAULT_CHARSET);
            out.writeInt(keyByteArray.length);
            out.writeBytes(keyByteArray);
            value = entry.getValue();
            marshallingEncoder.encode(value,out);

        }
        key = null; keyByteArray = null;  value = null;

        if (msg instanceof RequestMessage){

            Invocation invocation = ((RequestMessage) msg).getInvocation();
            if (invocation != null){
                out.writeByte(MessageType.SERVICE_REQ.value());
                marshallingEncoder.encode(invocation,out);
            }else {
                out.writeInt(0);
            }

        }else if (msg instanceof ResponseMessage){
            out.writeByte(MessageType.SERVICE_RESP.value());
            Object response = ((ResponseMessage)msg).getResponse();
            marshallingEncoder.encode(response,out);
            Throwable throwable = ((ResponseMessage)msg).getException();
            marshallingEncoder.encode(throwable,out);
        }else if (msg instanceof HeartBeatMessage){
            out.writeByte(MessageType.HEARTBEAT_REQ.value());
            out.writeInt(((HeartBeatMessage)msg ).getResult());

        }else if (msg instanceof HandShakeMessage){
            out.writeByte(MessageType.LOGIN_REQ.value());
            out.writeInt(((HandShakeMessage)msg).getResult());
        }
        out.setInt(4,out.readableBytes() - 8);


    }
}
