package com.github.thushear.cache;

import java.io.Serializable;

/**
 * Created by kongming on 2016/3/17.
 */
public abstract class BaseCacheDomain implements Serializable
{


    private String key;


    private String expiredTime;



    public abstract String generate(Object...objs);


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }
}
