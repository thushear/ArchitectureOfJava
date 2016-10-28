package com.github.thushear.msf.struct;

/**
 * 心跳消息 消息体为空
 * Created by kongming on 2016/10/28.
 */
public class HeartBeatMessage extends BaseMessage {

    private int result;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
