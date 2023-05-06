package com.github.longdt.netz.http;

import com.github.longdt.netz.http.transport.HttpTransport;
import com.github.longdt.netz.socket.TcpServer;

import java.io.IOException;

public class HttpServer {
  private final TcpServer server;

  protected HttpServer(int port) {
    server = TcpServer.newBuilder()
        .port(port)
        .connectionFactory(HttpConnection::new)
        .requestHandlerFactory(HttpTransport::new)
        .build();
  }

  public void start() throws IOException {
    server.start();
  }

  public void close() {
    server.close();
  }
}
