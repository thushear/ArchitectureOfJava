package com.github.thushear.shard;

import com.google.common.hash.*;

import java.nio.charset.Charset;

public class GuavaHashing {


    public static void main(String[] args) {

        HashFunction hashFunction = com.google.common.hash.Hashing.murmur3_128();
        HashCode hashCode = hashFunction.newHasher().putString("thushear", Charset.forName("UTF-8")).hash();
        System.out.println(hashCode.asLong());
    }
} 
