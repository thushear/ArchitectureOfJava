package com.github.thushear.msf.consumer;

import io.netty.channel.Channel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by kongming on 2016/11/7.
 */
public class MsgFuture<V> implements Future<V> {

    private volatile Object result;


    private final Channel channel;

    private int timeOut;

    public MsgFuture(Channel channel, int timeOut) {
        this.channel = channel;
        this.timeOut = timeOut;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        try {
            return get(timeOut,TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        synchronized (this){
            wait(timeout);
        }

        return (V) result;
    }

    public MsgFuture<V> setSuccess(V result){
        if (setSuccess0(result)){
            synchronized (this){
                notifyAll();
            }

            return this;
        }
        throw new IllegalStateException("complete already: " + this);
    }

    private boolean setSuccess0(V result) {

        if (isDone()) {
            return false;
        }

        synchronized (this) {
            // Allow only once.
            if (isDone()) {
                return false;
            }
            if (this.result == null) {

                this.result = result;

            }

        }
        return true;
    }




}
