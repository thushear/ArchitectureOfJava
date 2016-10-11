package com.github.thushear.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * Created by kongming on 2016/10/11.
 */
public class CuratorLearn {

  public static void main(String[] args) throws Exception {
    //创建客户端
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
//    CuratorFramework client =
//      CuratorFrameworkFactory.newClient("127.0.0.1:2181",5000,3000,retryPolicy);
//    client.start();
    //Fluent 创建含隔离命名空间的会话
    CuratorFramework client = CuratorFrameworkFactory.builder()
      .connectString("127.0.0.1:2181")
      .sessionTimeoutMs(5000)
      .retryPolicy(retryPolicy)
      .namespace("base")
      .build();

    client.start();
    //创建节点
    String path = "/zk-curator";
    client.create().creatingParentsIfNeeded()
      .withMode(CreateMode.EPHEMERAL)
      .forPath(path + "/c1","init".getBytes());

    Stat stat  = new Stat();
    byte[] data = client.getData().storingStatIn(stat).forPath(path + "/c1");
    System.out.println(new String(data) + ", stat =" + stat);
//    client.delete().deletingChildrenIfNeeded()
//      .withVersion(stat.getVersion())
//      .forPath(path);
    //更新数据
    client.setData().withVersion(stat.getVersion()).forPath(path+"/c1","test".getBytes());
    byte[] data1 = client.getData().storingStatIn(stat).forPath(path + "/c1");
    System.out.println(new String(data1) + ", stat =" + stat);

  }

}
