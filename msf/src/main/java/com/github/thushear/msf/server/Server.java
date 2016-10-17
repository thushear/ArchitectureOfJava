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


  public Server() {
    this.serverHandler = new ServerHandler();

  }

  public void registerProvider(Provider provider, ProviderInvoker providerInvoker){

    serverHandler.registerProvider(provider.getInterfaceName(),providerInvoker);
  }


  public static void start(){

  }

}
