package com.thushear.book.time.fnio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FNIOTimeServerHandler implements Runnable {


    private Socket socket ;


    public FNIOTimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        BufferedReader bufferedReader = null;
        PrintWriter out = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(),true);

            String currentTime = null;
            while (true){

                String body = bufferedReader.readLine();
                if (body == null){
                    break;
                }

                currentTime = "query".equals(body) ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "bad";
                out.println(currentTime);

            }


        } catch (IOException e) {

            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null){
                out.close();
            }

            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            this.socket = null;
        }


    }
}
