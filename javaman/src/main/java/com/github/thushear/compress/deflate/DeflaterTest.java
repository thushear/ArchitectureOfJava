package com.github.thushear.compress.deflate;

import java.io.UnsupportedEncodingException;
import java.util.zip.Deflater;

/**
 * Created by kongming on 2017/3/27.
 */
public class DeflaterTest {


    public static void main(String[] args) throws UnsupportedEncodingException {

        String input = "Hello snappy-java! Snappy-java is a JNI-based wrapper of Snappy, a fast compresser/decompresser." ;
//        byte[] inputBytes = input.getBytes("UTF-8");
//        byte[] outputBytes = new byte[1000];
//        Deflater deflater = new Deflater();
//        deflater.setInput(inputBytes);
//        deflater.finish();
//        deflater.deflate(outputBytes);
//
//        System.out.println();
        byte[] inputBytes = input.getBytes("UTF-8");

        byte[] compress =  DeflaterUtils.compress(input.getBytes("UTF-8"));

        System.out.println(inputBytes.length + "|" + compress.length);
    }
}
