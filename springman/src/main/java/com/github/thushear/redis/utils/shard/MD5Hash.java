package com.github.thushear.redis.utils.shard;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class MD5Hash implements Hashing {
    static final ThreadLocal<MessageDigest> md5Holder = new ThreadLocal() {
        protected MessageDigest initialValue() {
            try {
                return MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException var2) {
                throw new IllegalStateException("MD5 Algorithm is not found");
            }
        }
    };

    public MD5Hash() {
    }

    public long hash(String key) {
        return this.hash(SafeEncoder.encode(key));
    }

    public long hash(byte[] key) {
        MessageDigest md5 = (MessageDigest)md5Holder.get();
        md5.reset();
        md5.update(key);
        byte[] bKey = md5.digest();
        long res = (long)(bKey[3] & 255) << 24 | (long)(bKey[2] & 255) << 16 | (long)(bKey[1] & 255) << 8 | (long)(bKey[0] & 255);
        return res;
    }
}