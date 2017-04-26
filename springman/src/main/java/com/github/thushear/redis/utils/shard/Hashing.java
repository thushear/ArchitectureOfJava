package com.github.thushear.redis.utils.shard;

public interface Hashing {

    Hashing MURMUR_HASH = new MurmurHash();
    Hashing MD5 = new MD5Hash();
    Hashing CRC32 = new CRC32();

    long hash(String source);

    long hash(byte[] sourceBytes);

} 
