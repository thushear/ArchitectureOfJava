package com.thushear.book.time.nio;

public class NIOTimeServer {


    public static void main(String[] args) {

        MultiplexerTimeServer multiplexerTimeServer = new MultiplexerTimeServer(8080);

        new Thread(multiplexerTimeServer,"nio-server").start();

    }
} 
