package com.github.thushear.msf.util;

import com.github.thushear.msf.struct.Invocation;
import com.github.thushear.msf.struct.MessageHeader;
import com.github.thushear.msf.struct.MessageType;
import com.github.thushear.msf.struct.RequestMessage;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by kongming on 2016/11/7.
 */
public class MessageBuilder {


    private static final AtomicLong msgIncr = new AtomicLong(0);


    /**
     * Build request.
     *
     * @param clazz
     *         the class
     * @param methodName
     *         the method name
     * @param methodParamTypes
     *         the method param types
     * @param args
     *         the args
     * @return the request message
     */
    public static RequestMessage buildRequest(Class clazz, String methodName, Class[] methodParamTypes, Object[] args) {
        Invocation invocationBody = new Invocation();
        invocationBody.setArgs(args == null ? new Object[0] : args);
        invocationBody.setArgClasses(methodParamTypes);
        invocationBody.setArgsTypes(ClassLoaderUtils.getTypeStrs(methodParamTypes));
        invocationBody.setMethodName(methodName);
        invocationBody.setClassName(ClassLoaderUtils.getTypeStr(clazz));
        invocationBody.setInterfaceId(ClassLoaderUtils.getTypeStr(clazz));
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setInvocation(invocationBody);
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setSessionId(msgIncr.incrementAndGet());
        messageHeader.setType(MessageType.SERVICE_REQ.value());
        requestMessage.setHeader(messageHeader);

        return requestMessage;

    }
}
