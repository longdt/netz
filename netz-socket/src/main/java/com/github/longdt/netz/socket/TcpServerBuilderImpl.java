package com.github.longdt.netz.socket;

import java.nio.channels.SocketChannel;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TcpServerBuilderImpl implements TcpServer.Builder {
  int port;
  boolean reusePort;
  boolean blockingAccept;
  Supplier<Consumer<TcpConnection>> requestHandlerFactory;
  BiFunction<SocketChannel, LocalProvider, ? extends TcpConnection> connectionFactory = TcpConnection::new;

  public TcpServerBuilderImpl() {
  }

  public TcpServerBuilderImpl(TcpServer.Builder other) {
    this.port = other.port();
    this.reusePort = other.reusePort();
    this.blockingAccept = other.blockingAccept();
    this.requestHandlerFactory = other.requestHandlerFactory();
    this.connectionFactory = other.connectionFactory();
  }

  @Override
  public TcpServer.Builder port(int port) {
    this.port = port;
    return this;
  }

  @Override
  public int port() {
    return port;
  }

  @Override
  public TcpServer.Builder reusePort(boolean reusePort) {
    this.reusePort = reusePort;
    return this;
  }

  @Override
  public boolean reusePort() {
    return reusePort;
  }

  @Override
  public TcpServer.Builder blockingAccept(boolean blockingAccept) {
    this.blockingAccept = blockingAccept;
    return this;
  }

  @Override
  public boolean blockingAccept() {
    return blockingAccept;
  }

  @Override
  public TcpServer.Builder requestHandlerFactory(Supplier<Consumer<TcpConnection>> requestHandlerFactory) {
    this.requestHandlerFactory = requestHandlerFactory;
    return this;
  }

  @Override
  public Supplier<Consumer<TcpConnection>> requestHandlerFactory() {
    return requestHandlerFactory;
  }

  @Override
  public TcpServer.Builder connectionFactory(BiFunction<SocketChannel, LocalProvider, ? extends TcpConnection> connectionFactory) {
    this.connectionFactory = connectionFactory;
    return this;
  }

  @Override
  public BiFunction<SocketChannel, LocalProvider, ? extends TcpConnection> connectionFactory() {
    return connectionFactory;
  }

  @Override
  public TcpServer build() {
    return new TcpServer(this);
  }
}
