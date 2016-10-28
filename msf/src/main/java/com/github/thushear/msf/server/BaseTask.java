package com.github.thushear.msf.server;

import java.util.Comparator;

/**
 * Created by kongming on 2016/10/28.
 */
public abstract class BaseTask implements Runnable,Comparator<BaseTask> {

    abstract void  doRun();

    @Override
    public void run() {
        doRun();
    }

    @Override
    public int compare(BaseTask o1, BaseTask o2) {
        return 0;
    }
}
