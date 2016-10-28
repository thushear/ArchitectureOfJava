package com.github.thushear.msf.struct;

/**
 * 消息请求
 * Created by kongming on 2016/10/28.
 */
public class RequestMessage extends BaseMessage {


    private Invocation invocation;


    public Invocation getInvocation() {
        return invocation;
    }

    public void setInvocation(Invocation invocation) {
        this.invocation = invocation;
    }

    @Override
    public String toString() {
        return "RequestMessage{" +
                "invocation=" + invocation +
                "header=" + getHeader() +
                '}';
    }
}
