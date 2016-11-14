package com.github.thushear.async.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by kongming on 2016/11/11.
 */
public class LongEventFactory implements EventFactory<LongEvent> {


    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
