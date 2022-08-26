package com.github.longdt.netz.socket;

import com.github.longdt.netz.socket.concurrent.IOThread;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class EventLoop implements Runnable, Closeable {
    private final ServerSocketChannel serverSocketChannel;
    private final Selector selector;
    private final TcpServer.Builder builder;
    private BiFunction<SocketChannel, LocalProvider, ? extends TcpConnection> connectionFactory;
    private Consumer<TcpConnection> requestHandler;
    private LocalProvider localProvider;

    EventLoop(TcpServer.Builder builder) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEPORT, true);
        serverSocketChannel.bind(new InetSocketAddress(builder.port()));
        selector = Selector.open();
        this.builder = builder;
    }

    private void init() {
        localProvider = (IOThread) Thread.currentThread();
        connectionFactory = builder.connectionFactory();
        requestHandler = builder.requestHandlerFactory().get();
    }

    @Override
    public void run() {
        init();
        try {
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                int readyChannels = selector.select();
                if (readyChannels == 0)
                    continue;
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();
                    if (!key.isValid()) {
                        continue;
                    }
                    if (key.isReadable()) {
                        read(key);
                    } else if (key.isWritable()) {
                        write(key);
                    } else if (key.isAcceptable()) {
                        accept(key);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void read(SelectionKey key) throws IOException {
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
            buf.compact();
        } catch (IOException ignored) {
            connection.close();
        }
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        var connection = (TcpConnection) key.attachment();
        var data = connection.getOutBuffer();
        data.flip();
        try {
            channel.write(data);
            data.compact();
            if (data.position() == 0) {
                key.interestOps(SelectionKey.OP_READ);
            }
        } catch (IOException ignored) {
            connection.close();
        }
    }

    private void accept(SelectionKey key) throws IOException {
        var serverChannel = (ServerSocketChannel) key.channel();
        var channel = serverChannel.accept();
        channel.configureBlocking(false);
        var connection = connectionFactory.apply(channel, localProvider);
        var connKey = channel.register(key.selector(), SelectionKey.OP_READ, connection);
        connection.setSelectionKey(connKey);
    }

    @Override
    public void close() throws IOException {
        selector.close();
        serverSocketChannel.close();
    }
}
