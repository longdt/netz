package com.github.longdt.netz.socket;

import com.github.longdt.netz.socket.concurrent.IOThread;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class EventLoop implements Runnable, Closeable {
    protected final Selector selector;
    protected BiFunction<SocketChannel, LocalProvider, ? extends TcpConnection> connectionFactory;
    protected Consumer<TcpConnection> requestHandler;
    protected LocalProvider localProvider;

    EventLoop() throws IOException {
        this(null, null);
    }

    EventLoop(BiFunction<SocketChannel, LocalProvider, ? extends TcpConnection> connectionFactory, Consumer<TcpConnection> requestHandler) throws IOException {
        selector = Selector.open();
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
        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
        while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();
            keyIterator.remove();
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
    }

    void write(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        var connection = (TcpConnection) key.attachment();
        var data = connection.getOutBuffer();
        data.flip();
        try {
            channel.write(data);
//            data.compact();
            compact(data);
            if (data.position() == 0) {
                key.interestOps(SelectionKey.OP_READ);
            }
        } catch (IOException ignored) {
            connection.close();
        }
    }

    void read(SelectionKey key) {
        var channel = (SocketChannel) key.channel();
        var connection = (TcpConnection) key.attachment();
        var buf = connection.getInBuffer();
        try {
            int numRead = channel.read(buf);
            if (numRead == -1) {
                connection.close();
                key.cancel();
                return;
            }
            buf.flip();
            requestHandler.accept(connection);
//            buf.compact();
            compact(buf);
        } catch (IOException ignored) {
            connection.close();
        }
    }

    void compact(ByteBuffer buffer) {
        if (buffer.position() == buffer.limit()) {
            buffer.clear();
        }
        buffer.compact();
    }

    void accept(SelectionKey key) {
        var serverChannel = (ServerSocketChannel) key.channel();
        try {
            var channel = serverChannel.accept();
            channel.configureBlocking(false);
            var connection = connectionFactory.apply(channel, localProvider);
            var connKey = channel.register(key.selector(), SelectionKey.OP_READ, connection);
            connection.init(connKey);
        } catch (IOException e) {
            throw new RuntimeException("Acceptor Error", e);
        }
    }

    @Override
    public void close() throws IOException {
        selector.close();
    }
}
