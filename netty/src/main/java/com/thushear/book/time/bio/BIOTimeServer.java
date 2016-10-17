package com.thushear.book.time.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO 传统io 时间服务
 */
public class BIOTimeServer {


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);

            while (true){
                Socket socket = serverSocket.accept();
                new Thread(new BIOTimeServerHandler(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (serverSocket != null){
                serverSocket.close();
                serverSocket = null;
            }
        }
    }


} 
