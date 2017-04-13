package com.github.thushear.compress.lz4;

import net.jpountz.lz4.LZ4BlockOutputStream;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by kongming on 2017/3/28.
 */
public class LZ4Utils {


    public static byte[] compress(byte[] srcBytes) throws IOException {
        LZ4Factory lz4Factory = LZ4Factory.fastestInstance();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        LZ4Compressor lz4Compressor = lz4Factory.fastCompressor();
        LZ4BlockOutputStream lz4BlockOutputStream = new LZ4BlockOutputStream(baos,2048,lz4Compressor);
        lz4BlockOutputStream.write(srcBytes);
        lz4BlockOutputStream.close();
        return baos.toByteArray();
    }
}
