package com.github.thushear.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by kongming on 2016/11/14.
 */
public class JavaBioMan {


    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8080);
        while (true){

            Socket socket = serverSocket.accept();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line ;
            while ((line = bufferedReader.readLine())!=null){

                System.out.println(line);
            }

        }


    }

}
