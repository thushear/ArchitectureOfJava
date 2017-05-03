package com.github.thushear.redis.utils.shard;

import java.util.List;
import java.util.TreeMap;

/**
 * Created by kongming on 2017/4/26.
 */
public class MurmurHashShardPolicy implements ShardPolicy {


    TreeMap<Long,ShardInfo>  hash2Shard = new TreeMap<>();

    public MurmurHashShardPolicy(List<ShardInfo> shardInfoList){

        //构建红黑树存储分片算法
        for (int i = 0; i < shardInfoList.size(); i++) {
            ShardInfo shardInfo = shardInfoList.get(i);
            for (int j = 0; j < 160; j++) {
                hash2Shard.put(Hashing.MURMUR_HASH.hash("shardNode" + i + j),shardInfo);
            }

        }

    }


    @Override
    public ShardInfo getShard(String key) {
        if (key == null || "".equals(key.trim())){
            return null;
        }
        return hash2Shard.floorEntry(Hashing.MURMUR_HASH.hash(key)).getValue();
    }
}
