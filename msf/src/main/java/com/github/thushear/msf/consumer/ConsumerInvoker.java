package com.github.thushear.msf.consumer;

import com.github.thushear.msf.client.ClientChannelHandler;
import com.github.thushear.msf.struct.BaseMessage;
import com.github.thushear.msf.struct.MessageHeader;
import com.github.thushear.msf.struct.RequestMessage;
import com.github.thushear.msf.struct.ResponseMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by kongming on 2016/11/7.
 */
public class ConsumerInvoker {

    private Channel channel;


    private Consumer consumer;

    public ConsumerInvoker(Channel channel, Consumer consumer) {
        this.channel = channel;
        this.consumer = consumer;
    }


    public ResponseMessage invoke(BaseMessage request) throws InterruptedException, ExecutionException, TimeoutException {
        RequestMessage requestMessage = (RequestMessage) request;
        String methodName = requestMessage.getInvocation().getMethodName();
        MessageHeader header = requestMessage.getHeader();
        MsgFuture<ResponseMessage> msgFuture = new MsgFuture(channel,10000);
        ClientChannelHandler.registerMsg(header.getSessionId(),msgFuture);
        ChannelFuture  channelFuture = channel.writeAndFlush(requestMessage,channel.voidPromise());
        return msgFuture.get();

    }

}
