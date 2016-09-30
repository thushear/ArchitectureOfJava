package com.github.thushear.cache;

/**
 * Created by kongming on 2016/3/17.
 */
public abstract class AbstractKVCache extends BaseCacheDomain {


    private String value;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }




}
