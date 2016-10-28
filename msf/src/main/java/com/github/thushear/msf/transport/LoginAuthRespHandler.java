package com.github.thushear.msf.transport;

import com.github.thushear.msf.struct.BaseMessage;
import com.github.thushear.msf.struct.HandShakeMessage;
import com.github.thushear.msf.struct.MessageHeader;
import com.github.thushear.msf.struct.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 握手handler
 * Created by kongming on 2016/4/6.
 */
@SuppressWarnings("Duplicates")
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {

    private Map<String,Boolean> nodeCheck = new ConcurrentHashMap<>();

    private String[] whiteList = {"127.0.0.1"};

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BaseMessage message = (BaseMessage) msg;

        if (message.getHeader()!= null && message.getHeader().getType() == MessageType.LOGIN_REQ.value()){
            String nodeIndex = ctx.channel().remoteAddress().toString();
            HandShakeMessage loginResp = null;
            if (nodeCheck.containsKey(nodeIndex)){
                //重复登录 保护
                loginResp = buildResponse((byte)-1);
            }else {
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                String ip = address.getAddress().getHostAddress();
                boolean isOk = false;
                for (String wip : whiteList) {
                    if (wip.equals(ip)){
                        isOk = true;
                        break;
                    }
                }
                loginResp = isOk ? buildResponse((byte)0) : buildResponse((byte)-1);
                if (isOk){
                    nodeCheck.put(nodeIndex,true);
                }
                ctx.writeAndFlush(loginResp);
            }

        }else {
            ctx.fireChannelRead(msg);
        }

    }
    private HandShakeMessage buildResponse(byte result) {
        HandShakeMessage message = new HandShakeMessage();
        MessageHeader header = new MessageHeader();
        header.setType(MessageType.LOGIN_RESP.value());
        message.setHeader(header);
        message.setResult(result);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        nodeCheck.remove(ctx.channel().remoteAddress().toString());// 删除缓存
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }
}
