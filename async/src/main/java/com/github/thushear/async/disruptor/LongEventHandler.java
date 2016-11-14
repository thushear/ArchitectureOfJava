package com.github.thushear.async.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * Created by kongming on 2016/11/11.
 */
public class LongEventHandler implements EventHandler<LongEvent> {



    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("event:" + event);
    }
}
