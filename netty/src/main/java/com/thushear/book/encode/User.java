package com.thushear.book.encode;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by kongming on 2016/3/18.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private int userId;

    public User buildUsername(String username){
        this.username =username;
        return this;
    }

    public User buildUserId(int userId){
        this.userId = userId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] codeC(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byte[] value = this.username.getBytes();
        byteBuffer.putInt(value.length);
        byteBuffer.put(value);
        byteBuffer.putInt(this.userId);
        byteBuffer.flip();
        value = null;
        byte[] result = new byte[byteBuffer.remaining()];
        byteBuffer.get(result);
        return result;
    }
}
