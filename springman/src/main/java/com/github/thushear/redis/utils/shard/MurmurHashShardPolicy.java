package com.github.thushear.redis.utils.shard;

import java.util.List;
import java.util.TreeMap;

/**
 * Created by kongming on 2017/4/26.
 */
public class MurmurHashShardPolicy implements ShardPolicy {


    TreeMap<Long,ShardInfo>  hash2Shard ;

    public MurmurHashShardPolicy(List<ShardInfo> shardInfoList){


    }


}
