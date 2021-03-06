package com.thushear.example.factorial;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.math.BigInteger;
import java.util.List;

/**
 * Decodes the binary representation of a {@link BigInteger} prepended
 * with a magic number ('F' or 0x46) and a 32-bit integer length prefix into a
 * {@link BigInteger} instance.  For example, { 'F', 0, 0, 0, 1, 42 } will be
 * decoded into new BigInteger("42").
 */
public class BigIntegerDecoder extends ByteToMessageDecoder{

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // Wait until the length prefix is available.
        if (byteBuf.readableBytes() < 5){
            return;
        }
        byteBuf.markReaderIndex();

        int magicNumber = byteBuf.readUnsignedByte();
        if (magicNumber != 'F'){
            byteBuf.resetReaderIndex();
            throw new CorruptedFrameException("Invalid magic number: " + magicNumber);
        }
        // Wait until the whole data is available.
        int dataLength = byteBuf.readInt();
        if (byteBuf.readableBytes() < dataLength){
            byteBuf.resetReaderIndex();
            return;
        }
        // Convert the received data into a new BigInteger.
        byte[] decoded = new byte[dataLength];
        byteBuf.readBytes(decoded);

        list.add(new BigInteger(decoded));
    }
}
