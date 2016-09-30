package com.github.thushear.cache;

import java.util.List;

/**
 * Created by kongming on 2016/3/17.
 */
public interface CacheService {


    public void setex(AbstractKVCache kvCache) throws Exception;


    public void setnx(AbstractKVCache kvCache) throws Exception;


    public void mset(List<AbstractKVCache> kvCaches) throws Exception;


    public AbstractKVCache get(AbstractKVCache kvCache) throws Exception;


    public List<AbstractKVCache>  mget(List<AbstractKVCache> kvCaches) throws Exception;


    public void delete(AbstractKVCache kvCache) throws Exception;;


    public void hmset(AbstractHashCache abstractHashCache) throws Exception;

    public AbstractHashCache hget(AbstractHashCache abstractHashCache) throws Exception;

    public void hincrBy(AbstractHashCache abstractHashCache) throws Exception;


    public void zadd(AbstractSortedSetCache abstractSortedSetCache) throws Exception;

    public void zrange(AbstractSortedSetCache abstractSortedSetCache) throws Exception;

    public void zrevrange(AbstractSortedSetCache abstractSortedSetCache) throws Exception;



}
