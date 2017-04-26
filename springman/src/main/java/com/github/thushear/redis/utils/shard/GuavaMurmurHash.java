package com.github.thushear.redis.utils.shard;

public class GuavaMurmurHash implements Hashing {
    @Override
    public long hash(String source) {
        return this.hash(SafeEncoder.encode(source));
    }

    @Override
    public long hash(byte[] sourceBytes) {
        return com.google.common.hash.Hashing.murmur3_128().newHasher().putBytes(sourceBytes).hash().asLong();
    }
}
