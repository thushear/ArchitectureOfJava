package com.github.thushear.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by kongming on 2016/3/30.
 */
public class JavaSecurity {


    public static final String PUBLIC_KEY = "public.key";
    public static final String PRIVATE_KEY = "private.key";
    public static final String ALGORITHM = "RSA";

    public static void testAllProviders(){
        for (Provider provider : Security.getProviders()) {
            System.out.println(provider);
            System.out.println("===========================");
            for (Map.Entry<Object, Object> entry : provider.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
            System.out.println("===========================");
        }
    }


    /**
     * 摘要算法 目的是保证数据的完整性 常用于校验数据一致
     */
    public static void testMessageDigest(){

        try {
            byte[] input =  "thushear".getBytes();
            System.out.println("input:" + Arrays.toString(input));
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(input);
            byte[] output = md.digest();
            System.out.println("output:" + Arrays.toString(output));

            MessageDigest md2 = MessageDigest.getInstance("MD5");
            md2.update(input);
            byte[] output2 = md2.digest();
            System.out.println("output:" + Arrays.toString(output2));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    /**
     * 摘要流使用
     */
    public static void testDigestStream(){

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] input = "thushear".getBytes();
            DigestInputStream dis = new DigestInputStream(new ByteArrayInputStream(input),md5);
            dis.read(input,0,input.length);
            byte[] output = dis.getMessageDigest().digest();
            System.out.println("input:" + Arrays.toString(input));
            System.out.println("output:" + Arrays.toString(output));
            dis.close();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void testKeyPair(){

        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
            kpg.initialize(1024);
            KeyPair keyPair = kpg.genKeyPair();
            System.out.println(keyPair.getPublic());
            System.out.println(keyPair.getPrivate());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }


    public static void generateKeyPair() throws Exception {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(512);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        Key publicKey = keyPair.getPublic();

        Key privateKey = keyPair.getPrivate();

        ObjectOutputStream oos1 = null;
        ObjectOutputStream oos2 = null;

        try {
            oos1 = new ObjectOutputStream(new FileOutputStream(PUBLIC_KEY));
            oos2 = new ObjectOutputStream(new FileOutputStream(PRIVATE_KEY));
            oos1.writeObject(publicKey);
            oos2.writeObject(privateKey);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            oos1.close();
            oos2.close();
        }


    }


    public static String encrypt(String source) throws Exception {
        Key publicKey = null;
        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(new FileInputStream(PUBLIC_KEY));
            publicKey = (Key) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ois.close();
        }

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        byte[] input = source.getBytes();
        byte[] output = cipher.doFinal(input);

        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(output);

    }


    public static String decrypt(String crypto) throws Exception {
        Key privateKey = null;
        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(new FileInputStream(PRIVATE_KEY));
            privateKey = (Key) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            ois.close();
        }

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decodeBuf = decoder.decodeBuffer(crypto);
        byte[] output = cipher.doFinal(decodeBuf);

        return new String(output);
    }





    /**
     * 加密
     * @param content
     * @param password
     * @return
     */
    private static String encrypt(String content, String password) {
        try{
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128,
                    new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat,"AES");
            /**创建密码器**/
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("utf-8");
            /**初始化密码器**/
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(byteContent);
            return new String(result);
        }catch(Exception e) {
            System.out.println("出错了:" + e.getMessage());
        }
        return null;
    }


    /*
       * 加密
       * 1.构造密钥生成器
       * 2.根据ecnodeRules规则初始化密钥生成器
       * 3.产生密钥
       * 4.创建和初始化密码器
       * 5.内容加密
       * 6.返回字符串
       */
    public static String aesEncode(String encodeRules,String content) throws Exception {

            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            //3.产生原始对称密钥
            SecretKey original_key=keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte [] raw=original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte [] byte_encode=content.getBytes("utf-8");
            //9.根据密码器的初始化方式--加密：将数据加密
            byte [] byte_AES=cipher.doFinal(byte_encode);
            //10.将加密后的数据转换为字符串
            //这里用Base64Encoder中会找不到包
            //解决办法：
            //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
            String AES_encode=new String(new BASE64Encoder().encode(byte_AES));
            //11.将字符串返回
            return AES_encode;
    }



    /*
    * 解密
    * 解密过程：
    * 1.同加密1-4步
    * 2.将加密后的字符串反纺成byte[]数组
    * 3.将加密内容解密
    */
    public static String aesDncode(String encodeRules,String content) throws Exception {

            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            //3.产生原始对称密钥
            SecretKey original_key=keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte [] raw=original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte [] byte_content= new BASE64Decoder().decodeBuffer(content);
            /*
             * 解密
             */
            byte [] byte_decode=cipher.doFinal(byte_content);
            String AES_decode=new String(byte_decode,"utf-8");
            return AES_decode;

    }

    public static void main(String[] args) throws Exception {
//        testMessageDigest();
//        testDigestStream();
//        testKeyPair();
//        generateKeyPair();

////
//        String encrypt = encrypt("thushear");
//        System.out.println(encrypt);
//
//        String str = "cHlkKM/msfThox592tu8miYtmJr1zLKUAMFmWj3ByU5wBO0Ba8legTE/YLh+pA1B+5EcX15JQQmn\n" +
//                "vl/1sX5otw==";
//        System.out.println(str.length());
//
//        String decodeStr = decrypt(str);
//        System.out.println("decodeStr = " + decodeStr);

        String encodeStr = aesEncode("aes123","1000001");
        System.out.println("encodeStr = " + encodeStr);
        encodeStr = "nVQdLzzbPyBe2tjpjnBT5Q==";
        String decode =  aesDncode("aes123",encodeStr);
        System.out.println("decode = " + decode);

    }
}
