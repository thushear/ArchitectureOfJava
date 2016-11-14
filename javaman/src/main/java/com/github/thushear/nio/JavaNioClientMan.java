package com.github.thushear.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by kongming on 2016/11/14.
 */
public class JavaNioClientMan {


    public static void main(String[] args) throws IOException {

        connectClient();
    }

    private static void connectClient() throws IOException {
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        //
        socketChannel.configureBlocking(false);
        boolean connected = socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
        if (true) {
            //链路建立成功 注册读取事件
            socketChannel.register(selector,SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
//            socketChannel.register(selector, SelectionKey.OP_READ);
//            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//            byteBuffer.put("hello nio".getBytes());
//            byteBuffer.flip();
//
//            socketChannel.write(byteBuffer);
            while (true) {
                int readyChannels = selector.select();
                if (readyChannels == 0) continue;
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
                while (selectionKeyIterator.hasNext()) {
                    SelectionKey selectionKey = selectionKeyIterator.next();

                    if (selectionKey.isValid() && selectionKey.isConnectable()){
                        SocketChannel sc = (SocketChannel) selectionKey.channel();
                        if (sc.finishConnect()){
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            byteBuffer.put("hello nio".getBytes());
                            byteBuffer.flip();

                            socketChannel.write(byteBuffer);
                        }
                    }

                    if (selectionKey.isReadable()) {
                        // 通道Channel 可读就绪
                        // a channel is ready for reading
                        System.out.println("readable :" + selectionKey);
                        SocketChannel selectableChannel = (SocketChannel) selectionKey.channel();
                        System.out.println("selectableChannel = " + selectableChannel);
                        ByteBuffer buffer = ByteBuffer.allocate(1024);

                        int bytesRead = selectableChannel.read(buffer);
                        if (bytesRead > 0) {
                            //切换可读模式
                            buffer.flip();
                            byte[] bytes = new byte[buffer.remaining()];
                            //读取Buffer
                            buffer.get(bytes);
                            String readStr = new String(bytes);
                            System.out.println("readStr = " + readStr);


                        }


                    }
                }


            }


        }
    }

}
