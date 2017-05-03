package com.github.thushear.redis.utils.factory;

import com.github.thushear.redis.utils.shard.ShardInfo;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.api.async.RedisAsyncCommands;


/**
 * Created by kongming on 2017/5/3.
 */
public class LettuceAsyncCommandFactory {


    private LettuceAsyncCommandFactory(){}


    static RedisAsyncCommands<String,String> getAsyncCommand(ShardInfo shardInfo){
        RedisClient redisClient = RedisClient.create(RedisURI.Builder.redis(shardInfo.getHost(),shardInfo.getPort()).build());
        RedisAsyncCommands<String,String> asyncCommands = redisClient.connect().async();
        return asyncCommands;
    }

}
