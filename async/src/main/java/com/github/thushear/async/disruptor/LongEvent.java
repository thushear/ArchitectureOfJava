package com.github.thushear.async.disruptor;

/**
 * Created by kongming on 2016/11/11.
 */
public class LongEvent {

    private long value;

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LongEvent{" +
                "value=" + value +
                '}';
    }
}
