package com.github.longdt.netz.socket.concurrent;

import com.github.longdt.netz.socket.pool.Pool;
import com.github.longdt.netz.socket.pool.SimplePool;

import java.nio.ByteBuffer;

public class IOThread extends Thread {
    private final Pool<ByteBuffer> bufferPool = new SimplePool<>(256, () -> ByteBuffer.allocateDirect(2048));
    private final StringBuilder stringBuilder = new StringBuilder(1024);

    public IOThread(Runnable target) {
        super(target);
    }

    public Pool<ByteBuffer> getBufferPool() {
        return bufferPool;
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }
}
