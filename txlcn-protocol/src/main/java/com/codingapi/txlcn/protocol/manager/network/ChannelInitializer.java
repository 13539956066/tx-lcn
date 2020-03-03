package com.codingapi.txlcn.protocol.manager.network;


import com.codingapi.txlcn.protocol.Config;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.EventExecutorGroup;

public class ChannelInitializer extends io.netty.channel.ChannelInitializer<SocketChannel> {

  private final Config config;

  private final ObjectEncoder encoder;

  private final EventExecutorGroup peerChannelHandlerExecutorGroup;

  private final TMChannelHandler tmChannelHandler;

  private final TCChannelHandler tcChannelHandler;

  public ChannelInitializer(Config config, ObjectEncoder encoder,
      EventExecutorGroup peerChannelHandlerExecutorGroup,
      TMChannelHandler tmChannelHandler) {
    this.config = config;
    this.encoder = encoder;
    this.peerChannelHandlerExecutorGroup = peerChannelHandlerExecutorGroup;
    this.tmChannelHandler = tmChannelHandler;
    tcChannelHandler = new TCChannelHandler();
  }

  @Override
  protected void initChannel(final SocketChannel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();

    pipeline.addLast(new LengthFieldPrepender(4, false));
    pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));

    pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
    pipeline.addLast(encoder);
    pipeline.addLast(new IdleStateHandler(config.getMaxReadIdleSeconds(),
            0,
            0));

    pipeline.addLast(peerChannelHandlerExecutorGroup, tmChannelHandler,tcChannelHandler);
  }

}
