package com.github.thushear.msf.server;

import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Created by kongming on 2016/10/28.
 */
public class BusinessPool {


    /**
     * slf4j Logger for this class
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(BusinessPool.class);

    /**
     * 端口和业务线程池的对应缓存
     */
    private final static ConcurrentMap<Integer, ThreadPoolExecutor> poolMap = new ConcurrentHashMap<Integer, ThreadPoolExecutor>();

    private static volatile EventExecutorGroup serializeEventGroup;



//    /**
//     * 构建业务线程池
//     *
//     * @param transportConfig
//     *         ServerTransportConfig
//     * @return 线程池
//     */
//    public static ThreadPoolExecutor getBusinessPool(ServerTransportConfig transportConfig) {
//        int port = transportConfig.getPort();
//        ThreadPoolExecutor pool = poolMap.get(port);
//        if (pool == null) {
//            pool = CommonUtils.putToConcurrentMap(poolMap, port, initPool(transportConfig));
//        }
//        return pool;
//    }
//
//    /**
//     * 得到全部线程池
//     *
//     * @return 全部业务线程池
//     */
//    public static ConcurrentMap<Integer, ThreadPoolExecutor> getBusinessPools() {
//        return poolMap;
//    }

    /**
     * 按端口查询业务线程池
     *
     * @param port
     *         端口
     * @return 线程池
     */
    public static ThreadPoolExecutor getBusinessPool(int port) {
        return poolMap.get(port);
    }

//    private static synchronized ThreadPoolExecutor initPool(int minPoolSize, int maxPoolSize) {
//
//
//
//
//
//
//        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
//            private int i = 1;
//            @Override
//            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//                if (i++ % 7 == 0) {
//                    i = 1;
//                    LOGGER.warn("Task:{} has been reject for ThreadPool exhausted!" +
//                                    " pool:{}, active:{}, queue:{}, taskcnt: {}",
//                            new Object[]{
//                                    r,
//                                    executor.getPoolSize(),
//                                    executor.getActiveCount(),
//                                    executor.getQueue().size(),
//                                    executor.getTaskCount()
//                            });
//                }
//                throw new RejectedExecutionException("Biz thread pool of provider has bean exhausted");
//            }
//        };
//
//        return new ThreadPoolExecutor(minPoolSize, maxPoolSize,
//                1000 * 20, TimeUnit.MILLISECONDS,
//                configQueue, threadFactory, handler);
//    }

}
