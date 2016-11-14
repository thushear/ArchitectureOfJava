package com.github.thushear.async.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * Created by kongming on 2016/11/11.
 */
public class LongEventProducerWithTranslator {


    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }


    private static final EventTranslatorOneArg<LongEvent,ByteBuffer> TRANSLATOR_ONE_ARG =
            new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
                @Override
                public void translateTo(LongEvent event, long l, ByteBuffer byteBuffer) {
                    event.setValue(byteBuffer.getLong(0));
                }
            };

    public void onData(ByteBuffer byteBuffer){
        ringBuffer.publishEvent(TRANSLATOR_ONE_ARG,byteBuffer);
    }


}
