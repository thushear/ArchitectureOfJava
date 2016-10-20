package com.thushear.book.time.fnio;


import com.thushear.book.time.bio.BIOTimeServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FNIOTimeServer {


    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);

            while (true){
                Socket socket = serverSocket.accept();

                FNIOThreadPool fnioThreadPool =  new FNIOThreadPool(50,10000);
                fnioThreadPool.execute(new FNIOTimeServerHandler(socket));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                serverSocket = null;
            }
        }

    }

} 
