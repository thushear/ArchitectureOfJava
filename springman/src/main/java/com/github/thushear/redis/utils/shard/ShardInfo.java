package com.github.thushear.redis.utils.shard;

/**
 * Created by kongming on 2017/4/20.
 */
public class ShardInfo {

    private String host;

    private Integer port;


    public ShardInfo(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
