package com.thushear.book.serial.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.commons.codec.binary.Hex;

import java.util.Arrays;

/**
 * Created by kongming on 2016/3/21.
 */

/**
 * Protobuf的使用
 */
public class SubscribeReqProtoTest {


    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req = createSubscribeReq();
        System.out.println("before encode :" + req.toString());
        System.out.println("encode:" + Arrays.toString(req.toByteArray()));
        char[] hexChars = Hex.encodeHex(req.toByteArray());
        System.out.println(Hex.encodeHexString(req.toByteArray()));
        System.out.println("hex:" + Arrays.toString(hexChars));
        //
        SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
        System.out.println("aftere decode :" + req2.toString());
        assert req.equals(req2);
        System.out.println(req.equals(req2));
    }

    //decode
    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    //encode
    private static byte[] encode(SubscribeReqProto.SubscribeReq req){
        return req.toByteArray();
    }

    /**
     * 构建proto对象
     * @return
     */
    private static SubscribeReqProto.SubscribeReq createSubscribeReq(){
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setId(1);
        builder.setName("thushear");
        builder.setPhone("1234567890");
        return builder.build();
    }
}
