package com.thushear.book.serial.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by kongming on 2016/3/21.
 */
public class ProtobufSubServerHandler extends ChannelInboundHandlerAdapter {

    private SubscribeRespProto.SubscribeResp resp(int id){
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setReqId(id);
        builder.setCode(1);
        builder.setDesc("success");
        return builder.build();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq) msg;
        if ("thushear".equalsIgnoreCase(req.getName())){
            System.out.println("service accept " + req.toString());
        }
        ctx.writeAndFlush(resp(req.getId()));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
