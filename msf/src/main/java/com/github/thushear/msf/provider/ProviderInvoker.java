package com.github.thushear.msf.provider;

/**
 * Created by kongming on 2016/10/14.
 */
public class ProviderInvoker {


  private Provider provider;

  public ProviderInvoker(Provider provider) {
    this.provider = provider;
  }


  public Provider getProvider() {
    return provider;
  }

  public void setProvider(Provider provider) {
    this.provider = provider;
  }
}
