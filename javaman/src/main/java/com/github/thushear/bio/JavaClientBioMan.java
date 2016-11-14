package com.github.thushear.bio;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by kongming on 2016/11/14.
 */
public class JavaClientBioMan {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1",8080));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
        int size = 1024 * 1024;
        byte[] bytes = new byte[size];
        for (int i = 0; i < size; i++) {
            bytes[i] = 11;
        }
        String str = new String(bytes);
        writer.println(str);
        Thread.currentThread().join();
    }

}
