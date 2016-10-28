package com.github.thushear.msf.server;

import com.github.thushear.msf.provider.Provider;
import com.github.thushear.msf.provider.ProviderInvoker;
import com.github.thushear.msf.transport.NettyTransporter;

/**
 * Created by kongming on 2016/10/14.
 */
public class Server {


  private ServerHandler serverHandler;

  private NettyTransporter transporter;

  private transient boolean isStart = false;

  private String bindAddress;

  private int bindPort;


  public Server(String bindAddress, int bindPort) {
    this.bindAddress = bindAddress;
    this.bindPort = bindPort;
    this.serverHandler = new ServerHandler();
    transporter = new NettyTransporter();
  }

  public Server() {
    this.serverHandler = new ServerHandler();
    transporter = new NettyTransporter();

  }

  public void registerProvider(Provider provider, ProviderInvoker providerInvoker){

    serverHandler.registerProvider(provider.getInterfaceName(),providerInvoker);
  }


  public  void start(){
     if (isStart){
        return;
     }
     isStart = true;
    try {
      transporter.startNetty(bindAddress,bindPort,serverHandler);
    } catch (InterruptedException e) {
      throw new RuntimeException("server start error");
    }

  }

}
