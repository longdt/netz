package com.github.longdt.netz.socket;

import com.github.longdt.netz.socket.concurrent.IOThread;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TcpServer {
    private final Builder builder;

    protected TcpServer(Builder builder) {
        this.builder = builder;
    }

    public void start() throws IOException {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); ++i) {
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

        int port();

        Builder requestHandlerFactory(Supplier<Consumer<TcpConnection>> requestHandlerFactory);

        Supplier<Consumer<TcpConnection>> requestHandlerFactory();

        Builder connectionFactory(BiFunction<SocketChannel, LocalProvider, ? extends TcpConnection> connectionFactory);

        BiFunction<SocketChannel, LocalProvider, ? extends TcpConnection> connectionFactory();

        TcpServer build();
    }
}
