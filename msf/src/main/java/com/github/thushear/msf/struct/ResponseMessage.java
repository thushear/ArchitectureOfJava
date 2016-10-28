package com.github.thushear.msf.struct;

/**
 * 消息响应
 * Created by kongming on 2016/10/28.
 */
public class ResponseMessage extends BaseMessage {

    private Object response;

    private Throwable exception;

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "response=" + response +
                ", exception=" + exception +
                '}';
    }
}
