package com.github.thushear.exception;

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * architecture
 * created by kongming02
 * at 2019-08-07
 *
 **/
public class ExceptionCase {


    public static void main(String[] args) {


        try {
            error();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("====================");
            System.err.println(ExceptionUtils.getFullStackTrace(e));
        }

    }



    public static void error(){
        throw new NullPointerException();
    }


}
