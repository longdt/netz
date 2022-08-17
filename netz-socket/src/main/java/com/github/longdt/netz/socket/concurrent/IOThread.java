package com.github.longdt.netz.socket.concurrent;

import com.github.longdt.netz.socket.LocalProvider;
import com.github.longdt.netz.socket.pool.Pool;
import com.github.longdt.netz.socket.pool.SimplePool;

import java.nio.ByteBuffer;

public class IOThread extends Thread implements LocalProvider {
    private final Pool<ByteBuffer> bufferPool = new SimplePool<>(256, () -> ByteBuffer.allocateDirect(2048));
    private final StringBuilder stringBuilder = new StringBuilder(1024);
    private final byte[] tmpBuffer = new byte[2048];

    public IOThread(Runnable target) {
        super(target);
    }

    @Override
    public Pool<ByteBuffer> getBufferPool() {
        return bufferPool;
    }

    @Override
    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    @Override
    public byte[] getTmpBuffer() {
        return tmpBuffer;
    }
}
