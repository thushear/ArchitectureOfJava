package com.thushear.book.time.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable {


    private Selector selector;


    private ServerSocketChannel serverSocketChannel;


    private volatile boolean stop;

    public MultiplexerTimeServer(int port) {

        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("the time server is start in port:" + port);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }


    public void stop(){
        this.stop = true;
    }


    private void handleInput(SelectionKey key) throws IOException {

        if (key.isValid()){
            if (key.isAcceptable()){

                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);

                socketChannel.register(selector,SelectionKey.OP_READ);



            }

            if (key.isReadable()){
                //read the data
                SocketChannel sc = (SocketChannel) key.channel();

                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0){
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes,"UTF-8");
                    System.out.println("the time server receive order :" + body);
                    String currentTime = "q".equalsIgnoreCase(body)? LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "bad";

                    doWrite(sc,currentTime);
                }else if (readBytes < 0){
                    //close
                    key.cancel();
                    sc.close();
                }else ;

            }
        }
    }


    private void doWrite(SocketChannel channel,String response) throws IOException {

        if (Objects.nonNull(response) && response.trim().length() > 0){
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
        }
    }

    @Override
    public void run() {

        while (!stop){

            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()){
                    key = it.next();
                    it.remove();

                    try {
                        handleInput(key);
                    } catch (IOException e) {
                        key.cancel();
                        if (key.channel() != null){
                            key.channel().close();
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
