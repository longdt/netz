package com.github.longdt.netz.socket;

import java.io.IOException;
import java.net.StandardSocketOptions;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WorkerEventLoop extends EventLoop {
    private final TcpServer.Builder builder;
    private final Queue<SocketChannel> connectedChannels = new ConcurrentLinkedQueue<>();

    WorkerEventLoop(TcpServer.Builder builder) throws IOException {
        this.builder = builder;
    }

    @Override
    void init() {
        super.init();
        connectionFactory = builder.connectionFactory();
        requestHandler = builder.requestHandlerFactory().get();
    }

    @Override
    void handleEvents(int keyNum) {
        if (keyNum == 0) {
            doHandshake();
        }
        super.handleEvents(keyNum);
    }

    private void doHandshake() {
        SocketChannel channel;
        while ((channel = connectedChannels.poll()) != null) {
            try {
                channel.configureBlocking(false);
//                channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
//                channel.setOption(StandardSocketOptions.TCP_NODELAY, true);
//                channel.setOption(StandardSocketOptions.SO_RCVBUF, 16384);
//                channel.setOption(StandardSocketOptions.SO_SNDBUF, 16384);
                var connection = connectionFactory.apply(channel, localProvider);
                var connKey = channel.register(selector, SelectionKey.OP_READ, connection);
                connection.init(connKey);
            } catch (IOException ignored) {
                try {
                    channel.close();
                } catch (IOException ignored1) {
                }
            }
        }
    }

    protected void accept(SocketChannel channel) {
        connectedChannels.add(channel);
        selector.wakeup();
    }

    @Override
    public void close() throws IOException {
        super.close();
        SocketChannel channel;
        while ((channel = connectedChannels.poll()) != null) {
            channel.close();
        }
    }
}
