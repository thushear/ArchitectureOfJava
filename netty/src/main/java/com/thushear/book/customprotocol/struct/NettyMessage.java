package com.thushear.book.customprotocol.struct;

/**
 * Created by kongming on 2016/4/6.
 */
public final class NettyMessage {

    private Header header;

    private Object body;

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NettyMessage{");
        sb.append("body=").append(body);
        sb.append(", header=").append(header);
        sb.append('}');
        return sb.toString();
    }
}
