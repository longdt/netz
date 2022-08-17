package com.github.longdt.netz.socket;

import com.github.longdt.netz.socket.pool.Pool;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TcpServerBuilderImpl implements TcpServer.Builder {
    int port;
    Supplier<Consumer<TcpConnection>> requestHandlerFactory;
    BiFunction<SocketChannel, Pool<ByteBuffer>, ? extends TcpConnection> connectionFactory = TcpConnection::new;

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
    public TcpServer.Builder requestHandlerFactory(Supplier<Consumer<TcpConnection>> requestHandlerFactory) {
        this.requestHandlerFactory = requestHandlerFactory;
        return this;
    }

    @Override
    public Supplier<Consumer<TcpConnection>> requestHandlerFactory() {
        return requestHandlerFactory;
    }

    @Override
    public TcpServer.Builder connectionFactory(BiFunction<SocketChannel, Pool<ByteBuffer>, ? extends TcpConnection> connectionFactory) {
        this.connectionFactory = connectionFactory;
        return this;
    }

    @Override
    public BiFunction<SocketChannel, Pool<ByteBuffer>, ? extends TcpConnection> connectionFactory() {
        return connectionFactory;
    }

    @Override
    public TcpServer build() {
        return new TcpServer(this);
    }
}
