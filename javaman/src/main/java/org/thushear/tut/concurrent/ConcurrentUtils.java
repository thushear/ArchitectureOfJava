package org.thushear.tut.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by kongming on 2016/3/16.
 */
public class ConcurrentUtils {


    public static void stop(ExecutorService executorService){

        executorService.shutdown();
        try {
            executorService.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (!executorService.isTerminated()){
                System.err.println("kill non finished threads");
            }
            executorService.shutdownNow();
        }
    }
}
