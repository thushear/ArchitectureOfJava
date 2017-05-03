package com.github.thushear.redis.utils;

import com.github.thushear.redis.utils.shard.ShardInfo;
import com.github.thushear.redis.utils.shard.ShardPolicy;
import com.lambdaworks.redis.RedisFuture;
import com.lambdaworks.redis.api.async.RedisAsyncCommands;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by kongming on 2017/5/3.
 */
public class RedisUtils {


    private Map<ShardInfo,RedisAsyncCommands<String,String>> shard2Commands ;

    private ShardPolicy shardPolicy ;


    public RedisFuture<String> set(String key, String value){
        RedisAsyncCommands<String, String> redisAsyncCommands = getCommandByShard(key);
        return   redisAsyncCommands.set(key,value);
    }

    public RedisFuture<String> setex(String key,long seconds , String value){
        RedisAsyncCommands<String, String> redisAsyncCommands = getCommandByShard(key);
        return redisAsyncCommands.setex(key,seconds,value);
    }




    public RedisFuture<String> get(String key){
        RedisAsyncCommands<String, String> redisAsyncCommands = getCommandByShard(key);
        return redisAsyncCommands.get(key);
    }

    public String getAndWait(String key){
        RedisFuture<String> redisFuture = get(key);
        try {
            return redisFuture.get(1, TimeUnit.SECONDS);
        } catch (Throwable e) {
            throw new RuntimeException("getAndWait key " + key + " error because ",e);
        }
    }



    private RedisAsyncCommands<String, String> getCommandByShard(String key) {
        ShardInfo shardInfo = shardPolicy.getShard(key);
        return shard2Commands.get(shardInfo);
    }


    public void setShardPolicy(ShardPolicy shardPolicy) {
        this.shardPolicy = shardPolicy;
    }

    public void setShard2Commands(Map<ShardInfo, RedisAsyncCommands<String, String>> shard2Commands) {
        this.shard2Commands = shard2Commands;
    }
}
