package com.thushear.book.time.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BIOTimeClient {


    public static void main(String[] args) {

        BufferedReader bufferedReader = null;
        PrintWriter out = null;
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8080);

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("query");

            String body = null;
            body = bufferedReader.readLine();
            if (body == null) return;
            System.out.println(body);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (out != null) {
                out.close();
            }

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            socket = null;
        }

    }
} 
