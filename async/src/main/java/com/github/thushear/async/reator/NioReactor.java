package com.github.thushear.async.reator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by kongming on 2016/11/15.
 * Doug Lea Reactor 演示类
 *
 */
// Reactor
public class NioReactor  implements Runnable {


    final Selector selector;

    final ServerSocketChannel serverSocketChannel;

    public NioReactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        selectionKey.attach(new Acceptor());

    }


    public static void main(String[] args) throws IOException, InterruptedException {
        new Thread(new NioReactor(8080)).start();
    }

    //Dispatch Loop
    @Override
    public void run() {

        try {
            while (!Thread.currentThread().interrupted()){
                selector.select();
                Set<SelectionKey> selected = selector.selectedKeys();
                Iterator it = selected.iterator();
                while (it.hasNext())
                    dispatch((SelectionKey)it.next());
                selected.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void dispatch(SelectionKey k) {
        Runnable runnable = (Runnable) k.attachment();
        if (runnable != null) {
            runnable.run();
        }
    }


    //Acceptor
    class   Acceptor implements Runnable { // inner


        @Override
        public void run() {
            try {
                SocketChannel c = serverSocketChannel.accept();
                if (c != null) {
                    new Handler(selector,c);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
//Handler
final class Handler implements  Runnable{

    final int MAXIN = 2048;

    final int MAXOUT = 2048;

    final SocketChannel socket;

    final SelectionKey sk;

    ByteBuffer input = ByteBuffer.allocate(MAXIN);

    ByteBuffer output = ByteBuffer.allocate(MAXOUT);

    static final int READING = 0 , SENDING = 1;

    int state = READING;

    public Handler(Selector sel,SocketChannel c) throws IOException {
        socket = c;c.configureBlocking(false);

        sk = socket.register(sel,0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        sel.wakeup();
    }


    boolean inputIsComplete(){return true;}

    boolean outputIsComplete(){return true;}
    void process(){

        output.put("hello reactor".getBytes());
    }

    // Request Handling
    @Override
    public void run() {
        try {
            if (state == READING) read();
            else if (state == SENDING) send();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void read() throws IOException {
        socket.read(input);
        if (inputIsComplete()){
            process();
            state = SENDING;
            sk.interestOps(SelectionKey.OP_WRITE);
        }

    }

    void send() throws IOException {
        socket.write(output);

        if (outputIsComplete()){

            sk.cancel();

        }
    }

}
















