package com.github.thushear.redis.utils.factory;


import com.github.thushear.redis.utils.RedisUtils;
import com.github.thushear.redis.utils.shard.MurmurHashShardPolicy;
import com.github.thushear.redis.utils.shard.ShardInfo;
import com.github.thushear.redis.utils.shard.ShardPolicy;
import com.lambdaworks.redis.api.async.RedisAsyncCommands;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kongming on 2017/5/3.
 */
public class LettuceRedisFactoryBean implements FactoryBean , DisposableBean {


    private String shardInfos ;

    private Map<ShardInfo, RedisAsyncCommands<String, String>> SHARD_2_COMMANDS = new HashMap<>();


    @Override
    public Object getObject() throws Exception {
        if (shardInfos == null || "".equals(shardInfos.trim())){
            throw new RuntimeException("shardInfos can not be null or empty");
        }
        String[] shards = shardInfos.split(",");
        List<ShardInfo> shardInfoList = new ArrayList<>();
        for (int i = 0; i < shards.length; i++) {
            String[] hostAndPort = shards[i].split(":");
            ShardInfo shardInfo = new ShardInfo(hostAndPort[0],Integer.valueOf(hostAndPort[1]) );
            RedisAsyncCommands<String,String> redisAsyncCommand = LettuceAsyncCommandFactory.getAsyncCommand(shardInfo);
            SHARD_2_COMMANDS.put(shardInfo,redisAsyncCommand);
            shardInfoList.add(shardInfo);
        }
        ShardPolicy shardPolicy = new MurmurHashShardPolicy(shardInfoList);
        RedisUtils redisUtils = new RedisUtils();
        redisUtils.setShard2Commands(SHARD_2_COMMANDS);
        redisUtils.setShardPolicy(shardPolicy);
        return redisUtils;
    }

    @Override
    public Class<?> getObjectType() {
        return RedisUtils.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setShardInfos(String shardInfos) {
        this.shardInfos = shardInfos;
    }

    @Override
    public void destroy() throws Exception {
        if (SHARD_2_COMMANDS != null){
            for (Map.Entry<ShardInfo, RedisAsyncCommands<String, String>> asyncCommandsEntry : SHARD_2_COMMANDS.entrySet()) {
                asyncCommandsEntry.getValue().close();
            }
        }
    }
}
