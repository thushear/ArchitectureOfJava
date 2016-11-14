package com.github.thushear.async.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by kongming on 2016/11/11.
 */
public class LambdaLongEventMain {


    public static void main(String[] args) {

        // Executor that will be used to construct new threads for consumers
        Executor executor = Executors.newCachedThreadPool();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(LongEvent::new,bufferSize,executor);

        disruptor.handleEventsWith(((event, l, b) -> System.out.println("event:" + event)));
        // Start the Disruptor, starts all threads running
        disruptor.start();


        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer bb = ByteBuffer.allocate(8);

        bb.putLong(0,1l);

        ringBuffer.publishEvent(((event, sequence, buffer) -> event.setValue(buffer.getLong(0))),bb);



















    }




}
