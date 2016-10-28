package com.github.thushear.msf.server;

import com.github.thushear.msf.provider.ProviderInvoker;
import com.github.thushear.msf.struct.RequestMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kongming on 2016/10/14.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

  private static ConcurrentHashMap<String,ProviderInvoker> providerMap = new ConcurrentHashMap<>();

  /**
   * 业务线程池（一个端口一个）
   */
  private final ExecutorService bizThreadPool = Executors.newCachedThreadPool() ;// 业务线程池





  public ServerHandler() {

  }


  public void registerProvider(String providerKey,ProviderInvoker providerInvoker){
    providerMap.putIfAbsent(providerKey,providerInvoker);
  }


  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);
  }


  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    super.channelRead(ctx, msg);
    if (msg instanceof RequestMessage){

       RequestMessage requestMessage = (RequestMessage) msg;
       String interfaceName = requestMessage.getInvocation().getInterfaceId();
       ProviderInvoker providerInvoker = providerMap.get(interfaceName);
      if (providerInvoker == null)
         throw new RuntimeException("providerInvoker is null");
       bizThreadPool.execute(new MsfTask(ctx,requestMessage,providerInvoker));

    }
  }


  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    super.exceptionCaught(ctx, cause);
  }


}
