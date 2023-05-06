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

  public TcpConnection(SocketChannel socketChannel, LocalProvider localProvider) {
    this.socketChannel = socketChannel;
    bufferPool = localProvider.getBufferPool();
    inBuffer = bufferPool.get().clear();
    outBuffer = bufferPool.get().clear();
  }

  public void init(SelectionKey selectionKey) {
    this.selectionKey = selectionKey;
  }

  public void write(String data) {
    write(data, StandardCharsets.UTF_8);
  }

  public void write(String data, Charset charset) {
    write(data.getBytes(charset));
  }

  public void write(byte[] data) {
    outBuffer.put(data);
  }

  public void write(ByteBuffer data) {
    outBuffer.put(data);
  }

  public void flush() {
    try {
      outBuffer.flip();
      socketChannel.write(outBuffer);
      if (outBuffer.hasRemaining()) {
        selectionKey.interestOps(OP_READ_WRITE);
      } else {
        outBuffer.clear();
      }
    } catch (IOException e) {
      close();
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
  public void close() {
    if (closed)
      return;
    closed = true;
    bufferPool.release(inBuffer);
    bufferPool.release(outBuffer);
    try {
      socketChannel.close();
    } catch (IOException ignored) {
    }
  }
}
