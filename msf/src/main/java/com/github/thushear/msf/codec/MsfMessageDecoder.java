package com.github.thushear.msf.codec;

import com.github.thushear.msf.struct.*;
import com.github.thushear.msf.util.MsfConstants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kongming on 2016/10/28.
 */
public class MsfMessageDecoder extends LengthFieldBasedFrameDecoder{


    MarshallingDecoder marshallingDecoder;

    public MsfMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) throws IOException {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
        marshallingDecoder = new MarshallingDecoder();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
//        ByteBuf frame = in;
        ByteBuf frame = (ByteBuf) super.decode(ctx,in);
        if (frame == null)
            throw new RuntimeException("decode error");

        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setMagicCode(frame.readInt());
        messageHeader.setLength(frame.readInt());
        messageHeader.setSessionId(frame.readLong());
        messageHeader.setType(frame.readByte());
        messageHeader.setPriority(frame.readByte());
        int attachSize = frame.readInt();
        if (attachSize > 0) {
            Map<String,Object> attachMent = new HashMap<>();

            for (int i = 0; i < attachSize; i++) {
                int keyArraySize = frame.readInt();
                byte[] keyArray = new byte[keyArraySize];
                frame.readBytes(keyArray);
                String key = new String(keyArray, MsfConstants.DEFAULT_CHARSET);
                attachMent.put(key,marshallingDecoder.decode(frame));
            }
            messageHeader.setAttachment(attachMent);
        }

        if (frame.readableBytes() > 4){

            byte messageType = frame.readByte();
            if (MessageType.SERVICE_REQ.value() == messageType){
                RequestMessage requestMessage = new RequestMessage();
                Invocation invocation = (Invocation) marshallingDecoder.decode(frame);
                requestMessage.setInvocation(invocation);
                requestMessage.setHeader(messageHeader);
                return requestMessage;
            }else if (MessageType.SERVICE_RESP.value() == messageType){
                ResponseMessage responseMessage = new ResponseMessage();
                Object response = marshallingDecoder.decode(frame);
                Throwable throwable = (Throwable) marshallingDecoder.decode(frame);
                responseMessage.setResponse(response);
                responseMessage.setException(throwable);
                responseMessage.setHeader(messageHeader);
                return responseMessage;
            }

        }

        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setHeader(messageHeader);
        return baseMessage;
    }
}
