package com.github.thushear.compress.lz4;

import com.github.thushear.compress.deflate.DeflaterUtils;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by kongming on 2017/3/27.
 */
public class Lz4Test {

    public static void main(String[] args) throws IOException {
//        LZ4Factory factory = LZ4Factory.fastestInstance();
//        String input = "Hello snappy-java! Snappy-java is a JNI-based wrapper of Snappy, a fast compresser/decompresser." ;
//        byte[] inputBytes = input.getBytes("UTF-8");
//        LZ4Compressor compressor = factory.fastCompressor();
//        int maxCompressedLength = compressor.maxCompressedLength(inputBytes.length);
//        byte[] compressed = new byte[maxCompressedLength];
//        int compressedLength = compressor.compress(inputBytes,0,inputBytes.length,compressed,0,maxCompressedLength);
//
//        System.out.println("inputBytes length = " + inputBytes.length + "|" + compressedLength);
        String input = "Hello snappy-java! Snappy-java is a JNI-based wrapper of Snappy, a fast compresser/decompresser." ;
        byte[] inputBytes = input.getBytes("UTF-8");

        byte[] compress =  LZ4Utils.compress(input.getBytes("UTF-8"));

        System.out.println(inputBytes.length + "|" + compress.length);
    }

}
