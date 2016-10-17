package com.thushear.book.time.nio;

public class NIOTimeClient {


    public static void main(String[] args) {

        NIOTimeClientHandler nioTimeClientHandler = new NIOTimeClientHandler("127.0.0.1",8080);
        new Thread(nioTimeClientHandler).start();
    }
} 
