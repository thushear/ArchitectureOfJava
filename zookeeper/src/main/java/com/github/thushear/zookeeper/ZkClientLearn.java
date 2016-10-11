package com.github.thushear.zookeeper;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * Created by kongming on 2016/10/11.
 */
public class ZkClientLearn {


  public static void main(String[] args) throws InterruptedException {

    //创建会话
    ZkClient zkClient = new ZkClient("127.0.0.1:2181",5000);
    System.out.println(zkClient);

    //创建节点
    String path = "/zk-thushear";
//    zkClient.createPersistent(path,true);

    // subscribe
//    zkClient.subscribeChildChanges(path, new IZkChildListener() {
//      @Override
//      public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
//        System.out.println(parentPath + ",current childs = " + currentChilds);
//      }
//    });
//    zkClient.createPersistent(path);
//    Thread.sleep(1000);
//    System.out.println(zkClient.getChildren(path));
//    Thread.sleep(1000);
//    zkClient.createPersistent(path + "/c1");
//    Thread.sleep(1000);
//
//    zkClient.deleteRecursive(path);

    zkClient.createEphemeral(path,"123");
    zkClient.subscribeDataChanges(path, new IZkDataListener() {
      @Override
      public void handleDataChange(String dataPath, Object data) throws Exception {
        System.out.println("datapath=" + dataPath + " changed :" + data);
      }

      @Override
      public void handleDataDeleted(String dataPath) throws Exception {
        System.out.println("datapath=" + dataPath + " delete");
      }
    });


   // System.out.println( (String) zkClient.readData(path));
    zkClient.writeData(path,"456");
    Thread.sleep(1000);
    zkClient.deleteRecursive(path);
    Thread.sleep(Integer.MAX_VALUE);
  }
}
