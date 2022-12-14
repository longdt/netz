package com.github.longdt.netz.socket;

import com.github.longdt.netz.socket.concurrent.IOThread;

import java.io.Closeable;
import java.io.IOException;
import java.net.StandardSocketOptions;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class EventLoop implements Runnable, Closeable {
    protected final Selector selector;
    protected final Set<SelectionKey> selectedKeys;
    protected BiFunction<SocketChannel, LocalProvider, ? extends TcpConnection> connectionFactory;
    protected Consumer<TcpConnection> requestHandler;
    protected LocalProvider localProvider;

    EventLoop() throws IOException {
        this(null, null);
    }

    EventLoop(BiFunction<SocketChannel, LocalProvider, ? extends TcpConnection> connectionFactory, Consumer<TcpConnection> requestHandler) throws IOException {
        selector = Selector.open();
        selectedKeys = selector.selectedKeys();
        this.connectionFactory = connectionFactory;
        this.requestHandler = requestHandler;
    }

    void init() {
        localProvider = (IOThread) Thread.currentThread();
    }

    @Override
    public void run() {
        init();
        try {
            while (true) {
                handleEvents(selector.select());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void handleEvents(int keyNum) {
        for (var key : selectedKeys) {
            if (key.isValid()) {
                if (key.isWritable()) {
                    write(key);
                } else if (key.isReadable()) {
                    read(key);
                } else if (key.isAcceptable()) {
                    accept(key);
                }
            }
        }
        selectedKeys.clear();
    }

    void write(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        var connection = (TcpConnection) key.attachment();
        var buffer = connection.getOutBuffer();
        try {
            channel.write(buffer);
            if (!buffer.hasRemaining()) {
                buffer.clear();
                key.interestOps(SelectionKey.OP_READ);
            }
        } catch (IOException ignored) {
            connection.close();
        }
    }

    void read(SelectionKey key) {
        var channel = (SocketChannel) key.channel();
        var connection = (TcpConnection) key.attachment();
        var buffer = connection.getInBuffer();
        try {
            int numRead = channel.read(buffer);
            if (numRead == -1) {
                connection.close();
                key.cancel();
                return;
            }
            buffer.flip();
            requestHandler.accept(connection);
            if (!buffer.hasRemaining()) {
                buffer.clear();
            } else if (buffer.position() != 0) {
                buffer.compact();
            }
        } catch (IOException ignored) {
            connection.close();
        }
    }

    void accept(SelectionKey key) {
        var serverChannel = (ServerSocketChannel) key.channel();
        try {
            var channel = serverChannel.accept();
            configureChannel(channel);
            var connection = connectionFactory.apply(channel, localProvider);
            var connKey = channel.register(key.selector(), SelectionKey.OP_READ, connection);
            connection.init(connKey);
        } catch (IOException e) {
            throw new RuntimeException("Acceptor Error", e);
        }
    }

    void configureChannel(SocketChannel channel) throws IOException {
        channel.configureBlocking(false);
        channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        channel.setOption(StandardSocketOptions.TCP_NODELAY, true);
        channel.setOption(StandardSocketOptions.SO_RCVBUF, 16384);
        channel.setOption(StandardSocketOptions.SO_SNDBUF, 16384);
    }

    @Override
    public void close() throws IOException {
        selector.close();
    }
}
