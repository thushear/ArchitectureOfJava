package com.github.thushear.zookeeper;

import org.apache.curator.test.TestingCluster;
import org.apache.curator.test.TestingZooKeeperServer;

/**
 * Created by kongming on 2016/10/11.
 */
public class CuratorTestLearn {


  static String path = "/zookeeper";

  /**
   * 使用TestCluster模拟ZK集群
   * 杀死Leader之后 重新选举
   * @param args
   * @throws Exception
     */
  public static void main(String[] args) throws Exception {

    TestingCluster cluster = new TestingCluster(3);
    cluster.start();
    Thread.sleep(2000);

    TestingZooKeeperServer leader = null ;
    for (TestingZooKeeperServer zs : cluster.getServers()) {
      System.out.print(zs.getInstanceSpec().getServerId()+"-");
      System.out.print(zs.getQuorumPeer().getServerState() +"-");
      System.out.print(zs.getInstanceSpec().getDataDirectory().getAbsolutePath() +"-");
      System.out.println();
      if (zs.getQuorumPeer().getServerState().equals("leading")){
        leader = zs;
      }


    }

    leader.kill();
    System.out.println("after kill leader");
    for (TestingZooKeeperServer zs : cluster.getServers()) {
      System.out.print(zs.getInstanceSpec().getServerId()+"-");
      System.out.print(zs.getQuorumPeer().getServerState() +"-");
      System.out.print(zs.getInstanceSpec().getDataDirectory().getAbsolutePath() +"-");
      System.out.println();
    }

    cluster.stop();

  }


}
