package com.github.thushear.compress.snappy;

import org.xerial.snappy.Snappy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by kongming on 2017/3/27.
 */
public class SnappyTest {

    public static void main(String[] args) throws IOException {
        String input = "Hello snappy-java! Snappy-java is a JNI-based wrapper of Snappy, a fast compresser/decompresser.Hello snappy-java! Snappy-java is a JNI-based wrapper of Snappy, a fast compresser/decompresser.Hello snappy-java! Snappy-java is a JNI-based wrapper of Snappy, a fast compresser/decompresser.asfasefsalllfwfwofo" ;
        byte[] inputBytes = input.getBytes("UTF-8");
        byte[] compressed = Snappy.compress(inputBytes);
        byte[] uncompressed = Snappy.uncompress(compressed);

        System.out.println("inputBytes size = " + inputBytes.length + "|compressed size = " + compressed.length + "| uncompressed size =" + uncompressed.length);

    }


}
