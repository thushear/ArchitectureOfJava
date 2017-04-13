package com.github.thushear.compress.deflate;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Created by kongming on 2017/3/28.
 */
public class DeflaterUtils {

    public static byte[] compress(byte input[]) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Deflater deflater = new Deflater(1);
        try {
            deflater.setInput(input);
            deflater.finish();
            byte[] buf = new byte[2048];
            while (!deflater.finished()) {
                int count = deflater.deflate(buf);
                baos.write(buf, 0, count);
            }
        } finally {
            deflater.end();
        }
        return baos.toByteArray();
    }


    public static byte[] decompress(byte input[]) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();
        try {
            inflater.setInput(input);
            byte[] buf = new byte[2048];
            while (!inflater.finished()) {
                int count = inflater.inflate(buf);
                baos.write(buf, 0, count);
            }

        } catch (DataFormatException e) {
            e.printStackTrace();
        } finally {
            inflater.end();
        }
        return baos.toByteArray();
    }


}
