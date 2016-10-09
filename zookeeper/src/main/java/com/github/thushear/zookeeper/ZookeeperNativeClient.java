package com.github.thushear.zookeeper;

import com.alibaba.fastjson.JSON;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by kongming on 2016/10/9.
 */
public class ZookeeperNativeClient implements Watcher {


  private static CountDownLatch countDownLatch = new CountDownLatch(1);

  @Override
  public void process(WatchedEvent watchedEvent) {
    System.out.println(JSON.toJSONString(watchedEvent));
    if (Event.KeeperState.SyncConnected == watchedEvent.getState()){
      countDownLatch.countDown();
       if (Event.EventType.NodeChildrenChanged == watchedEvent.getType()){
         System.out.println("reget");
       }
    }

  }


  public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
    ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181",5000,new ZookeeperNativeClient());
    System.out.println(JSON.toJSONString( zooKeeper.getState()));
    System.out.println(zooKeeper.getSessionId());
    System.out.println(Arrays.toString(zooKeeper.getSessionPasswd()) );
    System.out.println(zooKeeper.getSessionTimeout());
    String syncPath1 = zooKeeper.create("/zk-test-ephemeral","test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    System.out.println("syncPath1 = " + syncPath1);
    String syncPath2 = zooKeeper.create("/zk-test-ephemeral","testtest".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
    System.out.println("syncPath2 = " + syncPath2);

    zooKeeper.create("/zk-test-ephemeral-async","test".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL,new IStringCallback(),"i am context");
    zooKeeper.create("/zk-test-ephemeral-async","test".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL,new IStringCallback(),"i am context");
    zooKeeper.create("/zk-test-ephemeral-async","test".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL,new IStringCallback(),"i am context");
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    String childPath = "/zk-learn1";
    zooKeeper.create(childPath,"test".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
    zooKeeper.create(childPath + "/c1","test".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
    List<String> childList = zooKeeper.getChildren(childPath,true);
    System.out.println("childList = " + childList);
    zooKeeper.create(childPath + "/c2","test".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
    System.out.println("zookeeper session");
    Thread.sleep(Integer.MAX_VALUE);
  }
}
class IStringCallback implements AsyncCallback.StringCallback {

  @Override
  public void processResult(int rc, String path, Object ctx, String name) {
    System.out.println("create path result [" + rc + ", path + " + path + ctx + name);
  }
}
