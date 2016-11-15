package com.github.thushear.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by kongming on 2016/11/14.
 */
public class JavaNioMan {

    public static void main(String[] args) throws IOException {
        System.out.println("JavaNioMan------------------------------------------");

//        basicBufferUsage();

//        fileChannelTransfer();
        fileChannelApi();
//        selectApi();

    }




    private static void fileChannelApi() throws IOException {

        FileInputStream inputStream = new FileInputStream(new File(JavaNioMan.class.getResource("/").getPath() + "aopbeans.xml"));
        FileChannel fileChannel = inputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        int bytesRead = fileChannel.read(byteBuffer);//read into buffer
        while (bytesRead != -1){
            System.out.println("read:" + bytesRead);
            byteBuffer.flip();//make buffer ready for read

            while (byteBuffer.hasRemaining()){
                System.out.print ((char) byteBuffer.get());// read 1 byte at a time
            }
            byteBuffer.clear();//make buffer ready for writing

            bytesRead = fileChannel.read(byteBuffer);
            System.out.println();
        }

        inputStream.close();
    }






    private static void selectApi() throws IOException {

        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //绑定端口
        serverSocketChannel.bind(new InetSocketAddress("0.0.0.0",8080),1024);
        //创建Selector 选择器
        Selector selector = Selector.open();
        //设置channel为非阻塞模式 FileChannel没有非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //注册ServerSocket Accept事件
        SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            int readyChannels = selector.select(); //当前就绪的通道
            if (readyChannels == 0 ) continue;
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()){
                SelectionKey selectionkey  = keyIterator.next();
                // 准备建立TCP就绪 建立TCP逻辑
                if (selectionkey.isAcceptable()){
                    // a connection was accepted by a ServerSocketChannel.
                    System.out.println("acceptable :" + selectionkey);
                    SelectableChannel selectableChannel = selectionkey.channel();
                    System.out.println("selectableChannel = " + selectableChannel);
                    //接收请求创建TCP 链路
                    SocketChannel socketChannel =  serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);//非阻塞模式
                    System.out.println("socketChannel = " + socketChannel);
                    //注册TCP链接到selector选择器
                    socketChannel.register(selector,SelectionKey.OP_READ
                            | SelectionKey.OP_WRITE
                            | SelectionKey.OP_CONNECT);
                    
                }else if (selectionkey.isConnectable()){
                    // a connection was established with a remote server.
                    System.out.println("connectable:" + selectionkey);
                    SocketChannel selectableChannel = (SocketChannel) selectionkey.channel();
                    selectableChannel.finishConnect();
                }else if (selectionkey.isReadable()){
                    // 通道Channel 可读就绪
                    // a channel is ready for reading
                    System.out.println("readable :" + selectionkey);
                    SocketChannel selectableChannel = (SocketChannel) selectionkey.channel();
                    System.out.println("selectableChannel = " + selectableChannel);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                    int bytesRead = selectableChannel.read(byteBuffer);
                    if (bytesRead > 0){
                        //切换可读模式
                        byteBuffer.flip();
                        byte[] bytes = new byte[byteBuffer.remaining()];
                        //读取Buffer
                        byteBuffer.get(bytes);
                        String readStr = new String(bytes);
                        System.out.println("readStr = " + readStr);

                        writeResponse(selectableChannel);

                    }else {
                        writeResponse(selectableChannel);
                    }
                    selectionkey.cancel();

                }
//                else if (selectionkey.isWritable()){
//                    System.out.println("writable :" + selectionkey);
//                    SocketChannel selectableChannel = (SocketChannel) selectionkey.channel();
//                    System.out.println("selectableChannel = " + selectableChannel);
//
//                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//                    byte[] bytes = "hello java  nio jdk8".getBytes();
//
//                    byteBuffer.put(bytes);
//
//                    selectableChannel.write(byteBuffer);
//                    selectableChannel.finishConnect();
//                    selectableChannel.shutdownOutput();
//                }
                keyIterator.remove();
            }
        }


    }


    private static void writeResponse(SocketChannel selectableChannel) throws IOException {
        String response = "hello java nio";
        byte[] writeBytes = response.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(writeBytes.length);
        writeBuffer.put(writeBytes);
        writeBuffer.flip();
        selectableChannel.write(writeBuffer);

    }


    private static void readBuffer(SocketChannel selectableChannel, ByteBuffer byteBuffer, int bytesRead) throws IOException {
        while (bytesRead != -1){
            System.out.println("read:" + bytesRead);
            byteBuffer.flip();//make buffer ready for read

            while (byteBuffer.hasRemaining()){
                System.out.print ((char) byteBuffer.get());// read 1 byte at a time
            }
            byteBuffer.clear();//make buffer ready for writing

            bytesRead = selectableChannel.read(byteBuffer);
            System.out.println();
        }
    }


    /**
     * 通道Channel 之间的数据传输
     * @throws IOException
     */
    private static void fileChannelTransfer() throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(JavaNioMan.class.getResource("/").getPath() + "aopbeans.xml","rw");
        FileChannel fromChannel = accessFile.getChannel();
        RandomAccessFile toaccessFile = new RandomAccessFile(JavaNioMan.class.getResource("/").getPath() + "to_aopbeans.xml","rw");
        FileChannel toChannel = toaccessFile.getChannel();
        long position = 0;
        long count = fromChannel.size();
        toChannel.transferFrom(fromChannel,position,count);

    }





    /**
     * Buffer 简单 实用 api
     * @throws IOException
     */
    private static void basicBufferUsage() throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(JavaNioMan.class.getResource("/").getPath() + "aopbeans.xml","rw");
        FileChannel fileChannel = accessFile.getChannel();
        //create buffer with capacity of 48 bytes
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        int bytesRead = fileChannel.read(byteBuffer);//read into buffer
        while (bytesRead != -1){
            System.out.println("read:" + bytesRead);
            byteBuffer.flip();//make buffer ready for read

            while (byteBuffer.hasRemaining()){
                System.out.print ((char) byteBuffer.get());// read 1 byte at a time
            }
            byteBuffer.clear();//make buffer ready for writing

            bytesRead = fileChannel.read(byteBuffer);
            System.out.println();
        }

        accessFile.close();
    }


}
