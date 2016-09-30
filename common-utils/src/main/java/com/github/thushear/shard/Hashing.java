package com.github.thushear.shard;

public interface Hashing {

    Hashing MURMUR_HASH = new MurmurHash();
    Hashing MD5 = new MD5Hash();
    Hashing CRC32 = new CRC32();
    Hashing GUAVA_MURMUR = new GuavaMurmurHash();

    long hash(String source);

    long hash(byte[] sourceBytes);

} 
