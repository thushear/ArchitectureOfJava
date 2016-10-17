package com.github.thushear.msf.provider;

import com.github.thushear.msf.server.Server;
import com.github.thushear.msf.util.ClassLoaderUtils;

/**
 * Created by kongming on 2016/10/14.
 */
public class Provider<T> {


  private T refImpl;

  private String interfaceName;

  private Server server;


  public Provider(T refImpl, String interfaceName) {
    this.refImpl = refImpl;
    this.interfaceName = interfaceName;
  }


  public void export() {

    ProviderInvoker providerInvoker = new ProviderInvoker(this);
    assert server != null;
    server.registerProvider(this, providerInvoker);
    server.start();
  }


  public String getInterfaceName() {
    return interfaceName;
  }

  public void setInterfaceName(String interfaceName) {
    this.interfaceName = interfaceName;
  }

  public T getRefImpl() {
    return refImpl;
  }

  public void setRefImpl(T refImpl) {
    this.refImpl = refImpl;
  }

  public Server getServer() {
    return server;
  }

  public void setServer(Server server) {
    this.server = server;
  }
}
