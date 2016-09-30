package com.github.thushear.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES 加密解密工具类
 */
public class AESUtils {

    //算法名称
    private static final String KEY_ALGORITHM = "AES";
    //算法名称/加密模式/填充方式
    public static final String CIPHER_ALGORITHM = KEY_ALGORITHM + "/ECB/PKCS5Padding";
    //key长度
    private static final int KEY_LEN = 128;


    private static final String encryotKey = "kflcvcOc00Ww1oIzYQV7Tw==";


    public static String encrypt(String content) throws Exception {
        return encrypt(content,encryotKey);
    }

    public static String decrypt(String content) throws Exception {
        return decrypt(content,encryotKey);
    }



    public static byte[] encryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);

        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
        secureRandom.setSeed(encryptKey.getBytes("utf-8"));
        kgen.init(KEY_LEN,secureRandom);

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), KEY_ALGORITHM));

        return cipher.doFinal(content.getBytes("utf-8"));
    }

    public static String decryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);

        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
        secureRandom.setSeed(decryptKey.getBytes("utf-8"));
        kgen.init(KEY_LEN,secureRandom);

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), KEY_ALGORITHM));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);

        return new String(decryptBytes);
    }

    public static String encrypt(String content, String encryptKey) throws Exception {
        if (content==null || content.isEmpty() )
            return "";

        return bytesToHexString((encryptToBytes(content, encryptKey)));
    }

    public static String decrypt(String content, String decryptKey) throws Exception {
        if (content==null || content.isEmpty() )
            return "";
        return decryptByBytes(hexStringToBytes(content), decryptKey);
    }




    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return "";
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }


    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
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

    public static void main(String[] args) throws Exception {
//        System.out.println("0f305a78340a8aa0e60afc3de956f9e8".length());
//        String content = "1234567890123";
//        String key = "https://bbs.taobao.com/u/state/0f305a78340a8aa0e60afc3de956f9e8.htm?spm=a210m.1001016.0.0.4ThHZL";
//        String decode = encrypt(content,key);
//        System.out.println("decode = " + decode);
//        System.out.println(decode.length());
//        String source = decrypt(decode,key);
//        System.out.println("source = " + source);

        String encrypt = encrypt("1234567890");
        System.out.println("encrypt = " + encrypt);
        String decrypt = decrypt(encrypt);
        System.out.println("decrypt = " + decrypt);

    }
}