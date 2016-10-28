package com.github.thushear.msf.server;

import com.github.thushear.msf.provider.ProviderInvoker;
import com.github.thushear.msf.struct.RequestMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by kongming on 2016/10/28.
 */
public class MsfTask extends BaseTask {


    private ChannelHandlerContext ctx;

    private RequestMessage requestMessage;

    private ProviderInvoker providerInvoker;

    public MsfTask(ChannelHandlerContext ctx, RequestMessage requestMessage,ProviderInvoker providerInvoker) {
        this.ctx = ctx;
        this.requestMessage = requestMessage;
        this.providerInvoker = providerInvoker;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public RequestMessage getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(RequestMessage requestMessage) {
        this.requestMessage = requestMessage;
    }

    @Override
    void doRun() {

        String className = requestMessage.getInvocation().getClassName();
        String methodName = requestMessage.getInvocation().getMethodName();

    }
}
