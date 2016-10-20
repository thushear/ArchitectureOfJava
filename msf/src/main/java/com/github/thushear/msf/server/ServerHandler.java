package com.github.thushear.msf.server;

import com.github.thushear.msf.provider.ProviderInvoker;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kongming on 2016/10/14.
 */
public class ServerHandler {

  private static ConcurrentHashMap<String,ProviderInvoker> providerMap = new ConcurrentHashMap<>();

  public ServerHandler() {
  }


  public void registerProvider(String providerKey,ProviderInvoker providerInvoker){
    providerMap.putIfAbsent(providerKey,providerInvoker);
  }



}
