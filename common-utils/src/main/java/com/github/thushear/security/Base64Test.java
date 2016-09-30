package com.github.thushear.security;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by kongming on 2016/3/31.
 */
public class Base64Test {

    public static void main(String[] args) {

        String base = Base64.encodeBase64URLSafeString("123456789".getBytes());
        System.out.println("base = " + base);

        String base1 = Base64.encodeBase64URLSafeString("abcdefghijk".getBytes());
        System.out.println("base = " + base1);


    }
}
