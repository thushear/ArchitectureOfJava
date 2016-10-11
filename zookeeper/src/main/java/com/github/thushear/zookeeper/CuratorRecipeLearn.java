package com.github.thushear.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Created by kongming on 2016/10/11.
 * 典型应用场景 learn
 */
public class CuratorRecipeLearn {

  static String getDistributed_incr = "/zkcurator-distributed-incr";

  static String path = "/zk-curator/nodecache";

  static String master_path = "/zk-curator-master";

  static String distributed_lock = "/zkcurator-distributed-lock";

  static CuratorFramework client = CuratorFrameworkFactory.builder()
                                      .connectString("127.0.0.1:2181")
                                      .sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000,3)).build();




  public static void main(String[] args) throws Exception {

    client.start();
//    client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
//      .forPath(path,"init".getBytes());

    //典型场景 -- 节点监听
//    final NodeCache cache = new NodeCache(client,path,false);
//    cache.start(true);
//    cache.getListenable().addListener(new NodeCacheListener() {
//      @Override
//      public void nodeChanged() throws Exception {
//        System.out.println("new data:" + new String(cache.getCurrentData().getData()) );
//      }
//    });
//    client.setData().forPath(path,"test".getBytes());
//    Thread.sleep(1000);
//    client.delete().deletingChildrenIfNeeded().forPath(path);
//    Thread.sleep(Integer.MAX_VALUE);
//
    //--------------Master选举-----

//    LeaderSelector selector = new LeaderSelector(client, master_path, new LeaderSelectorListenerAdapter() {
//      @Override
//      public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
//        System.out.println("get master "  );
//        Thread.sleep(30000);
//        System.out.println("release masater");
//      }
//    });
//
//    selector.autoRequeue();
//    selector.start();


    //----------分布式锁场景-----------

//    final InterProcessMutex lock = new InterProcessMutex(client,distributed_lock);
//
//    final CountDownLatch countDownLatch = new CountDownLatch(1);
//
//    for (int i = 0; i < 30; i++) {
//      new Thread(new Runnable() {
//        @Override
//        public void run() {
//          try {
//            countDownLatch.await();
//            lock.acquire();
//
//
//          SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss|SSS");
//          String orderNO = simpleDateFormat.format(new Date());
//          System.out.println("orderNO = " + orderNO);
//          } catch ( Exception e) {
//            e.printStackTrace();
//          }finally {
//            try {
//              lock.release();
//            } catch (Exception e) {
//              e.printStackTrace();
//            }
//          }
//
//        }
//      }).start();
//    }
//
//    countDownLatch.countDown();


    //----------分布式计数器

    DistributedAtomicInteger atomicInteger = new DistributedAtomicInteger(client,getDistributed_incr,new RetryNTimes(3,1000));

    AtomicValue<Integer> atomicValue = atomicInteger.add(8);

    System.out.println(atomicValue.succeeded()+ ";" + atomicValue.postValue());

    Thread.sleep(Integer.MAX_VALUE);


  }


}
