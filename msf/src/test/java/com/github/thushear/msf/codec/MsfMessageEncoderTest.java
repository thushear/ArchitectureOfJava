package com.github.thushear.msf.codec;

import com.github.thushear.msf.struct.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import junit.framework.TestCase;
import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by kongming on 2016/10/28.
 */
public class MsfMessageEncoderTest extends TestCase {

    private MsfMessageEncoder msfMessageEncoder ;

    @Override
    protected void setUp() throws Exception {
        msfMessageEncoder = new MsfMessageEncoder();

    }

    public void testRequestMessageCodec() throws Exception {

        RequestMessage requestMessage = new RequestMessage();
        MessageHeader  header = new MessageHeader();
        header.setSessionId(1l);
        header.setType(MessageType.LOGIN_REQ.value());
        header.setPriority((byte)1);
        header.setAttachment(new HashMap<>());
        Invocation invocation = new Invocation();
        invocation.setClassName("com.github.thushear.msf.codec.MsfMessageEncoder");
        invocation.setMethodName("encode");
        invocation.setAlias("base");
        String[] argsTypes = new String[]{"io.netty.channel.ChannelHandlerContext"
                ,"com.github.thushear.msf.struct.BaseMessage","io.netty.buffer.ByteBuf"};
        invocation.setArgsTypes(argsTypes);
        requestMessage.setHeader(header);
        requestMessage.setInvocation(invocation);

        ByteBuf byteBuf = Unpooled.buffer(4096);

        msfMessageEncoder.encode(null,requestMessage,byteBuf);
        byte[] bytes = byteBuf.array();
        System.out.println(Arrays.toString(bytes));
//        for (byte aByte : bytes) {
//            String hex = Integer.toHexString(aByte);
//            System.out.print(hex);
//        }

        char[] hexChars = Hex.encodeHex(bytes);
        System.out.println(new String(hexChars));

        Object decodeMsg = new MsfMessageDecoder(1024 * 1024, 4, 4).decode(null,byteBuf);
        System.out.println(decodeMsg instanceof RequestMessage);
        System.out.println(decodeMsg);

    }



    public void testResponseMessageCodec() throws Exception {

        ResponseMessage responseMessage = new ResponseMessage();
        MessageHeader  header = new MessageHeader();
        header.setSessionId(1l);
        header.setType(MessageType.LOGIN_REQ.value());
        header.setPriority((byte)1);
        header.setAttachment(new HashMap<>());

        responseMessage.setHeader(header);
        responseMessage.setResponse(Boolean.TRUE);
        responseMessage.setException(new NullPointerException());

        ByteBuf byteBuf = Unpooled.buffer(4096);

        msfMessageEncoder.encode(null,responseMessage,byteBuf);
        byte[] bytes = byteBuf.array();
        System.out.println(Arrays.toString(bytes));
//        for (byte aByte : bytes) {
//            String hex = Integer.toHexString(aByte);
//            System.out.print(hex);
//        }

        char[] hexChars = Hex.encodeHex(bytes);
        System.out.println(new String(hexChars));

        Object decodeMsg = new MsfMessageDecoder(1024 * 1024, 4, 4).decode(null,byteBuf);
        System.out.println(decodeMsg instanceof ResponseMessage);
        System.out.println(decodeMsg);



    }



    public void testRequestMessageCodecWithArgs() throws Exception {

        RequestMessage requestMessage = new RequestMessage();
        MessageHeader  header = new MessageHeader();
        header.setSessionId(1l);
        header.setType(MessageType.SERVICE_REQ.value());
        header.setPriority((byte)1);
        header.setAttachment(new HashMap<>());
        Invocation invocation = new Invocation();
        invocation.setClassName("com.github.thushear.msf.service.HelloService");
        invocation.setMethodName("say");
        invocation.setAlias("base");
        String[] argsTypes = new String[]{"java.lang.String"};
        invocation.setArgsTypes(argsTypes);
        Object[] args = new Object[1];
        args[0] = "world";
        invocation.setArgs(args);
        requestMessage.setHeader(header);
        requestMessage.setInvocation(invocation);

        ByteBuf byteBuf = Unpooled.buffer(4096);

        msfMessageEncoder.encode(null,requestMessage,byteBuf);
        byte[] bytes = byteBuf.array();
        System.out.println(Arrays.toString(bytes));
//        for (byte aByte : bytes) {
//            String hex = Integer.toHexString(aByte);
//            System.out.print(hex);
//        }

        char[] hexChars = Hex.encodeHex(bytes);
        System.out.println(new String(hexChars));

        Object decodeMsg = new MsfMessageDecoder(1024 * 1024, 4, 4).decode(null,byteBuf);
        System.out.println(decodeMsg instanceof RequestMessage);
        System.out.println(decodeMsg);

    }

}