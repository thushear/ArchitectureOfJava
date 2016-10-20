package com.thushear.example.factorial;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.math.BigInteger;

/**
 * Encodes a {@link Number} into the binary representation prepended with
 * a magic number ('F' or 0x46) and a 32-bit length prefix.  For example, 42
 * will be encoded to { 'F', 0, 0, 0, 1, 42 }.
 */

public class NumberEncoder extends MessageToByteEncoder<Number>{
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Number number, ByteBuf byteBuf) throws Exception {
        BigInteger v ;
        if (number instanceof BigInteger){
            v = (BigInteger) number;
        }else{
            v = new BigInteger(String.valueOf(number));
        }

        byte[] data  = v.toByteArray();
        int dataLength = data.length;

        //简单协议
        byteBuf.writeByte((byte)'F');
        byteBuf.writeInt(dataLength);
        byteBuf.writeBytes(data);
    }
}
