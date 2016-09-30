package com.github.thushear.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kongming on 2016/3/17.
 */
public abstract class AbstractHashCache extends BaseCacheDomain {


    private  Map<String,String>  cacheMap = new HashMap<String, String>();

    private String filed;

    private String value;


    public void addEntry(String filed,String value){
        cacheMap.put(filed, value);
    }

    public Map<String, String> getCacheMap() {
        return cacheMap;
    }

    public void setCacheMap(Map<String, String> cacheMap) {
        this.cacheMap = cacheMap;
    }


}
