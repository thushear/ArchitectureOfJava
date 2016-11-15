package com.github.thushear.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by kongming on 2016/11/15.
 */
public class TimeServer {

    private static Charset charset = Charset.forName("US-ASCII");

    private static CharsetEncoder encoder = charset.newEncoder();


    /**
     *  这个例子使用了两种方式。 accept使用了回调的方式， 而发送数据使用了future的方式。
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(Executors.newFixedThreadPool(4));
        AsynchronousServerSocketChannel asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open(group).bind(new InetSocketAddress("0.0.0.0",8088));
        asynchronousServerSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            @Override
            public void completed(AsynchronousSocketChannel result, Void attachment) {
                asynchronousServerSocketChannel.accept(null,this);//接受下一個連接
                String now = new Date().toString();
                try {
                    ByteBuffer byteBuffer = encoder.encode(CharBuffer.wrap(now + "\r\n"));
                    Future<Integer> f = result.write(byteBuffer);
                    f.get();
                    System.out.println("send to client :" + now);
                    result.close();
                } catch (CharacterCodingException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                exc.printStackTrace();
            }
        });

        group.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    }




}
