package com.thushear.book.serial;

import java.io.Serializable;

/**
 * Created by kongming on 2016/3/18.
 */
public class SubscribeResp implements Serializable {

    private static final long serialVersionUID = 1L;


    private int reqId;

    private int code;

    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getReqId() {
        return reqId;
    }

    public void setReqId(int reqId) {
        this.reqId = reqId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SubscribeResp{");
        sb.append("code=").append(code);
        sb.append(", reqId=").append(reqId);
        sb.append(", desc='").append(desc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
