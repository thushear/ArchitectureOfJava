package com.github.thushear.basic;

/**
 * Created by kongming on 2017/5/24.
 */
public class ExceptionTest {


    public static void main(String[] args) {

        String s = null;

        try {
            s.getBytes();
        }catch (NullPointerException e){
            System.err.println("e " + e);
            throw new IllegalArgumentException(e);
        }catch (IllegalArgumentException e){
            System.err.println("ill " + e);
            throw new RuntimeException(e);
        }

    }
}
