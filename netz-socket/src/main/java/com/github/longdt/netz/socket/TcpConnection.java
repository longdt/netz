package com.github.longdt.netz.socket;

import com.github.longdt.netz.socket.pool.Pool;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TcpConnection implements Closeable {
    private static final int OP_READ_WRITE = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
    private final SocketChannel socketChannel;
    private SelectionKey selectionKey;
    private final ByteBuffer inBuffer;
    private final ByteBuffer outBuffer;
    private final Pool<ByteBuffer> bufferPool;
    private boolean closed;

    protected TcpConnection(SocketChannel socketChannel, Pool<ByteBuffer> bufferPool) {
        this.socketChannel = socketChannel;
        inBuffer = bufferPool.get().clear();
        outBuffer = bufferPool.get().clear();
        this.bufferPool = bufferPool;
    }

    public void write(String data) {
        write(data, StandardCharsets.UTF_8);
    }

    public void write(String data, Charset charset) {
        write(data.getBytes(charset));
    }

    public void write(byte[] data) {
        var newWrite = (outBuffer.position() == 0);
        outBuffer.put(data);
        interestWriteIf(newWrite);
    }

    public void write(ByteBuffer data) {
        var newWrite = (outBuffer.position() == 0);
        outBuffer.put(data);
        interestWriteIf(newWrite);
    }

    private void interestWriteIf(boolean flag) {
        if (flag) {
            selectionKey.interestOps(OP_READ_WRITE);
        }
    }

    public ByteBuffer getInBuffer() {
        return inBuffer;
    }

    public ByteBuffer getOutBuffer() {
        return outBuffer;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    @Override
    public void close() throws IOException {
        if (closed)
            return;
        closed = true;
        socketChannel.close();
        bufferPool.release(inBuffer);
        bufferPool.release(outBuffer);
    }

    public void setSelectionKey(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
    }
}
