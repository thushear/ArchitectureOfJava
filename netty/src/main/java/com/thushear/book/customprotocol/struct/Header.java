package com.thushear.book.customprotocol.struct;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kongming on 2016/4/6.
 */
public final class Header {

    private int crcCode = 0xabef0101;

    private int length; //消息长度

    private long sessionID;//会话id

    private byte type;//消息类型

    private byte priority;//消息优先级

    private Map<String,Object> attachment = new HashMap<>();//附件


    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    public int getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public long getSessionID() {
        return sessionID;
    }

    public void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Header{");
        sb.append("attachment=").append(attachment);
        sb.append(", crcCode=").append(crcCode);
        sb.append(", length=").append(length);
        sb.append(", sessionID=").append(sessionID);
        sb.append(", type=").append(type);
        sb.append(", priority=").append(priority);
        sb.append('}');
        return sb.toString();
    }
}
