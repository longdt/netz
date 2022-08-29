package com.github.longdt.netz.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;

public class ServerEventLoop extends EventLoop {
    protected final ServerSocketChannel serverSocketChannel;
    private final TcpServer.Builder builder;

    ServerEventLoop(TcpServer.Builder builder) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEPORT, builder.reusePort());
        serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        serverSocketChannel.bind(new InetSocketAddress(builder.port()), 16 * 1024);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        this.builder = builder;
    }

    @Override
    void init() {
        super.init();
        connectionFactory = builder.connectionFactory();
        requestHandler = builder.requestHandlerFactory().get();
    }

    @Override
    public void close() throws IOException {
        super.close();
        serverSocketChannel.close();
    }
}
