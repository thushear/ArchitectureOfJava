package com.github.thushear.utils.charset;

import java.io.UnsupportedEncodingException;

/**
 * Created by kongming on 2016/9/30.
 */
public class CharLearning {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s1 = "\uD834\uDD1E";
        System.out.println("s1 = " + s1);
        ////"你好"的GBK编码数据
        byte[] gbkData = {(byte)0xc4,(byte)0xe3,(byte)0xba, (byte) 0xc3};

        //构造String，解码为Unicode
        String strFromGBK = new String(gbkData,"GBK");
        System.out.println("strFromGBK = " + strFromGBK);
        showUnicode(strFromGBK);
        System.out.println();
        String unicode = "\u4f60\u597d";
        showBytes(unicode,"GBK");
        showBytes(unicode,"UTF-8");
    }


    public static void showUnicode(String str){
        for (int i = 0; i < str.length(); i++) {
            System.out.printf("\\u%x",(int)str.charAt(i));
        }
    }

    public static void showBytes(String str,String charSet) throws UnsupportedEncodingException {
        for (byte b : str.getBytes(charSet)) {
            System.out.printf("0x%x",b);

        }
        System.out.println();
    }
}
