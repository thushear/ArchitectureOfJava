package com.github.thushear.security;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by kongming on 2016/4/1.
 */
public final class AESCodec {

    /**
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "AES";


    /**
     * 加密/解密算法 / 工作模式 / 填充模式
     */
    public static final String CIPHER_ALOGRITHM = "AES/ECB/PKCS5Padding";


    public static void main(String[] args) throws Exception {
//        String input = "1234567890";
//        System.out.println("input:" + input);
//        //初始化密钥
//        byte[] key = initKey();
//        System.out.println("密钥:" + Arrays.toString(key));
//        System.out.println("密钥:" + Base64.encodeBase64String(key));
//
//        byte[] output = encrypt(input.getBytes(),key);
//        System.out.println("output:" + new String(output));
//        System.out.println("output:" + Base64.encodeBase64URLSafeString(output));
//
//
//        byte[] outputdata = decrypt(output,key);
//        String outputStr = new String(outputdata);
//        System.out.println("outputStr = " + outputStr);

        System.out.println(genKey());

    }


    public static byte[] decrypt(byte[] data,byte[] key) throws Exception {
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALOGRITHM);
        cipher.init(Cipher.DECRYPT_MODE,k);
        return cipher.doFinal(data);
    }



    public static byte[] encrypt(byte[] data,byte[] key) throws Exception {
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALOGRITHM);
        cipher.init(Cipher.ENCRYPT_MODE,k);
        return cipher.doFinal(data);
    }

    /**
     * 生成密钥
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] initKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    public static String genKey() throws Exception {
        return Base64.encodeBase64String(initKey());
    }

    private static Key toKey(byte[] key){
        SecretKey secretKey = new SecretKeySpec(key,KEY_ALGORITHM);
        return secretKey;
    }

}
