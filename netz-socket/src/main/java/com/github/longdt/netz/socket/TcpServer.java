package com.github.longdt.netz.socket;

import com.github.longdt.netz.socket.concurrent.IOThread;
import com.github.longdt.netz.socket.pool.Pool;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TcpServer {
    private final TcpServerBuilderImpl builder;

    TcpServer(TcpServerBuilderImpl builder) {
        this.builder = builder;
    }

    public void start() throws IOException {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors() * 2; ++i) {
            Thread t = new IOThread(new EventLoop(builder));
            t.start();
        }
    }

    public void close() {

    }

    public static Builder newBuilder() {
        return new TcpServerBuilderImpl();
    }

    public interface Builder {
        Builder port(int port);
        Builder requestHandlerFactory(Supplier<Consumer<TcpConnection>> requestHandlerFactory);
        Builder connectionFactory(BiFunction<SocketChannel, Pool<ByteBuffer>, ? extends TcpConnection> connectionFactory);
        TcpServer build();
    }
}
