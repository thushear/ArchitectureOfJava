package com.github.thushear.msf.server;

import com.github.thushear.msf.provider.ProviderInvoker;
import com.github.thushear.msf.struct.MessageType;
import com.github.thushear.msf.struct.RequestMessage;
import com.github.thushear.msf.struct.ResponseMessage;
import com.github.thushear.msf.util.ReflectUtils;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setHeader(requestMessage.getHeader().clone());
        responseMessage.getHeader().setType(MessageType.SERVICE_RESP.value());
        String className = requestMessage.getInvocation().getClassName();
        String methodName = requestMessage.getInvocation().getMethodName();
        String[] argsClasses = requestMessage.getInvocation().getArgsTypes();
        try {
            Method method = ReflectUtils.getMethod(className,methodName,argsClasses);
            Object responseObj = method.invoke(providerInvoker.getProvider().getRefImpl(),requestMessage.getInvocation().getArgs());
            responseMessage.setResponse(responseObj);
        } catch (ClassNotFoundException e) {
            responseMessage.setException(e);
        } catch (NoSuchMethodException e) {
            responseMessage.setException(e);
        } catch (InvocationTargetException e) {
            responseMessage.setException(e);
        } catch (IllegalAccessException e) {
            responseMessage.setException(e);
        }
        ctx.writeAndFlush(responseMessage);

    }
}
