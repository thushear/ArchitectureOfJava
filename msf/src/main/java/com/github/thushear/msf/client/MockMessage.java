package com.github.thushear.msf.client;

import com.github.thushear.msf.struct.Invocation;
import com.github.thushear.msf.struct.MessageHeader;
import com.github.thushear.msf.struct.MessageType;
import com.github.thushear.msf.struct.RequestMessage;

import java.util.HashMap;

/**
 * Created by kongming on 2016/11/1.
 */
public class MockMessage {



    public static RequestMessage buildRequestMessage(){

        RequestMessage requestMessage = new RequestMessage();
        MessageHeader header = new MessageHeader();
        header.setSessionId(1l);
        header.setType(MessageType.SERVICE_REQ.value());
        header.setPriority((byte)1);
        header.setAttachment(new HashMap<>());
        Invocation invocation = new Invocation();
        invocation.setClassName("com.github.thushear.msf.service.HelloService");
        invocation.setInterfaceId("com.github.thushear.msf.service.HelloService");
        invocation.setMethodName("say");
        invocation.setAlias("base");
        String[] argsTypes = new String[]{"java.lang.String"};
        invocation.setArgsTypes(argsTypes);
        Object[] args = new Object[1];
        args[0] = "world";
        invocation.setArgs(args);
        requestMessage.setHeader(header);
        requestMessage.setInvocation(invocation);

        return requestMessage;

    }


}
