package com.github.thushear.redis.utils.shard;

public class CRC32 implements Hashing{
    public CRC32() {
    }

    public long hash(String key) {
        return this.hash(SafeEncoder.encode(key));
    }

    public long hash(byte[] key) {
        java.util.zip.CRC32 crc32 = new java.util.zip.CRC32();
        crc32.update(key);
        return crc32.getValue();
    }
}
