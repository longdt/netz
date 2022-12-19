package com.github.longdt.netz.socket;

import org.jctools.queues.SpscArrayQueue;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Queue;

public class WorkerEventLoop extends EventLoop {
    private final TcpServer.Builder builder;
    private final Queue<SocketChannel> connectedChannels = new SpscArrayQueue<>(16 * 1024);

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
                configureChannel(channel);
                var connection = connectionFactory.apply(channel, localProvider);
                var connKey = channel.register(selector, SelectionKey.OP_READ, connection);
                connection.init(connKey);
                read(connKey); //try read early first
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
