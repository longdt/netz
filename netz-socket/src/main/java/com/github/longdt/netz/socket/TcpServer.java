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
        var eventloops = new EventLoop[Runtime.getRuntime().availableProcessors()];
        for (int i = 0; i < eventloops.length; ++i) {
            eventloops[i] = builder.reusePort() ? new ServerEventLoop(builder) : new WorkerEventLoop(builder);
            Thread t = new IOThread(eventloops[i], "el-" + i);
            t.start();
        }
        if (!builder.reusePort()) {
            Thread t = new IOThread(new ServerEventLoop(builder) {
                int counter = 0;

                @Override
                void handleEvents(int keyNum) {
                    for (var i = 0; i < keyNum; ++i) {
                        try {
                            var channel = serverSocketChannel.accept();
                            ((WorkerEventLoop) eventloops[(counter++ % eventloops.length) & 0xFF]).accept(channel);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    selectedKeys.clear();
                }
            }, "el-acceptor");
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

        Builder reusePort(boolean reusePort);

        boolean reusePort();

        Builder blockingAccept(boolean blockingAccept);

        boolean blockingAccept();

        Builder requestHandlerFactory(Supplier<Consumer<TcpConnection>> requestHandlerFactory);

        Supplier<Consumer<TcpConnection>> requestHandlerFactory();

        Builder connectionFactory(BiFunction<SocketChannel, LocalProvider, ? extends TcpConnection> connectionFactory);

        BiFunction<SocketChannel, LocalProvider, ? extends TcpConnection> connectionFactory();

        TcpServer build();
    }
}
