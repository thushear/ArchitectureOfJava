package com.github.thushear.cache;

import java.util.List;

/**
 * Created by kongming on 2016/3/17.
 */
public class CacheServiceImpl implements CacheService {

    @Override
    public void setex(AbstractKVCache kvCache) throws Exception {

        kvCache.getKey();
        kvCache.getValue();
        kvCache.getExpiredTime();

    }

    @Override
    public void setnx(AbstractKVCache kvCache) throws Exception {

    }

    @Override
    public void mset(List<AbstractKVCache> kvCaches) throws Exception {

    }

    @Override
    public AbstractKVCache get(AbstractKVCache kvCache) throws Exception {
        return null;
    }

    @Override
    public List<AbstractKVCache> mget(List<AbstractKVCache> kvCaches) throws Exception {
        return null;
    }

    @Override
    public void delete(AbstractKVCache kvCache) throws Exception {

    }

    @Override
    public void hmset(AbstractHashCache abstractHashCache) throws Exception {
        abstractHashCache.getKey();
        abstractHashCache.getExpiredTime();
        abstractHashCache.getCacheMap();
    }

    @Override
    public AbstractHashCache hget(AbstractHashCache abstractHashCache) throws Exception {

        return null;
    }

    @Override
    public void hincrBy(AbstractHashCache abstractHashCache) throws Exception {

    }

    @Override
    public void zadd(AbstractSortedSetCache abstractSortedSetCache) throws Exception {

        abstractSortedSetCache.getKey();
        abstractSortedSetCache.getMember();
        abstractSortedSetCache.getScore();
    }

    @Override
    public void zrange(AbstractSortedSetCache abstractSortedSetCache) throws Exception {

        abstractSortedSetCache.getStart();
        abstractSortedSetCache.getEnd();
    }

    @Override
    public void zrevrange(AbstractSortedSetCache abstractSortedSetCache) throws Exception {

    }
}
