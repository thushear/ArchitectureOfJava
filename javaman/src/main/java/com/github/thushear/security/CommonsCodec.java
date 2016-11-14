package com.github.thushear.security;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by kongming on 2016/3/31.
 */
public class CommonsCodec {


    public static void main(String[] args) throws DecoderException {

        byte[] input = "abcdefg".getBytes();
        byte[] data = Base64.encodeBase64(input);
        System.out.println("data = " + new String(data));

        byte[] decodedata = Base64.decodeBase64(data);
        System.out.println("decodedata = " + new String(decodedata));

        byte[] urlSafeData = Base64.encodeBase64URLSafe(input);
        System.out.println("urlSafeData = " + new String(urlSafeData));
        byte[] urlSafeDecode = Base64.decodeBase64(urlSafeData);
        System.out.println("urlSafeDecode = " + new String(urlSafeDecode));

        byte[] hexInput = "123456789".getBytes();
        String hex = Hex.encodeHexString(hexInput);
        System.out.println("hex = " + hex);
        byte[] hexdata = Hex.decodeHex(hex.toCharArray());
        System.out.println("hexdata = " + new String(hexdata));

        String md5 = DigestUtils.md5Hex("123456789");
        System.out.println("md5 = " + md5);
        String sha =  DigestUtils.sha1Hex("123456789");
        System.out.println("sha = " + sha);


        String base64 = Base64.encodeBase64URLSafeString("123456789412".getBytes());
        System.out.println("base64 = " + base64);
        String hex64 = Hex.encodeHexString(base64.getBytes());
        System.out.println("hex64 = " + hex64);

        byte[] decodeHexBase64 = Base64.decodeBase64(Hex.decodeHex(hex64.toCharArray()));
        System.out.println("decodeHexBase64 = " + new String(decodeHexBase64));

    }
}
