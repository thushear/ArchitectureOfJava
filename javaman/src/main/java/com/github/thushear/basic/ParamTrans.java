package com.github.thushear.basic;

/**
 * Created by kongming on 2017/3/30.
 */
public class ParamTrans {




    public static void main(String[] args) {
        int a = 1;int b =2;
        String aa = "aa"; String bb = "bb";
        swap(a,b);
        swapString(aa,bb);
        System.out.println("b = " + b);
        System.out.println("a = " + a);
        System.out.println("aa = " + aa);
        System.out.println("bb = " + bb);

        byte first = (byte)1;
        System.out.println(first & (byte)15);


    }


    public static void swapString(String a ,String b){
        String c = a;
        a = b;
        b = c;
    }

    public static void swap(int a ,int b){
        int c = a;
        a = b;
        b = c;
    }
}
