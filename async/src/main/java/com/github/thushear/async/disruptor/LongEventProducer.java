package com.github.thushear.async.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * Created by kongming on 2016/11/11.
 */
public class LongEventProducer {


    private final RingBuffer<LongEvent> ringBuffer;


    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer byteBuffer){

        long sequence = ringBuffer.next(); // Grab the next sequence

        try {
            LongEvent event  = ringBuffer.get(sequence);// Get the entry in the Disruptor
            event.setValue(byteBuffer.getLong(0));// Fill with data
        } finally {
            ringBuffer.publish(sequence);
        }

    }
}
