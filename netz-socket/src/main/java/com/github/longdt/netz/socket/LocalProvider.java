package com.github.longdt.netz.socket;

import com.github.longdt.netz.socket.pool.Pool;

import java.nio.ByteBuffer;

public interface LocalProvider {
    Pool<ByteBuffer> getBufferPool();

    StringBuilder getStringBuilder();

    byte[] getTmpBuffer();
}
