package com.github.thushear.msf.struct;

import java.io.Serializable;

/**
 * Created by kongming on 2016/10/28.
 */
public  class BaseMessage  implements Serializable {


    private MessageHeader header;


    public MessageHeader getHeader() {
        return header;
    }

    public void setHeader(MessageHeader header) {
        this.header = header;
    }

}
