//package com.github.thushear.shard;
//
//import java.nio.charset.Charset;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class HashTest {
//
//
//    public static void main(String[] args) {
////        String hashSource = "thushear";
////        Hashing murmur = new MurmurHash();
////        long hash =  murmur.hash(hashSource);
////        System.out.println("hash = " + hash);
////        System.out.println(hash % 128);
////
////        long crc32 = Hashing.CRC32.hash(hashSource);
////        System.out.println("crc32 = " + crc32);
////
////        long md5 = Hashing.MD5.hash(hashSource);
////        System.out.println("md5 = " + md5);
//
//        testHash( hashSource -> Math.abs(Hashing.MURMUR_HASH.hash(hashSource)) );
//
//        testHash( hashSource -> Hashing.MD5.hash(hashSource));
//
//        testHash( hashSource -> Hashing.CRC32.hash(hashSource));
//
////        testHash( hashSource -> Math.abs(com.google.common.hash.Hashing.murmur3_128().newHasher().putString(hashSource, Charset.forName("UTF-8")).hash().asLong()) );
//
//        testHash( hashSource -> Math.abs(Hashing.GUAVA_MURMUR.hash(hashSource)) );
//    }
//
//    public static void testHash(HashFunc hashFunc){
//        Map<Long,Integer> count = new ConcurrentHashMap<>();
//        for (int i = 0; i < 10000; i++) {
//            long hash = hashFunc.hash(i + "");
//            long shard =  hash%64;
//            Integer cnt = count.get(shard);
//            if (cnt == null){
//                count.put(shard,1);
//            }else{
//                count.put(shard,cnt + 1);
//            }
//
//        }
//        System.out.println(count);
//        System.out.println("=================");
//    }
//
//    @FunctionalInterface
//    interface HashFunc {
//        long hash(String hashSource);
//    }
//
//}
