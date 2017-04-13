package com.github.thushear.compress.snappy;

import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * Created by kongming on 2017/3/28.
 */
public class SnappyUtils {

    public static byte[] compress(byte[] srcBytes) throws IOException {
        return Snappy.compress(srcBytes);
    }
}
