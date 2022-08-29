package com.github.longdt.netz.socket.transport;

import com.github.longdt.netz.socket.TcpConnection;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class SimpleHttpTransport implements Consumer<TcpConnection> {
    private static final byte[] body = "Hello World".getBytes(StandardCharsets.UTF_8);
    private static final byte[] header = ("HTTP/1.1 200 OK\r\nConnection: Keep-Alive\r\nContent-Length: " + body.length + "\r\n\r\n").getBytes(StandardCharsets.UTF_8);
    private final ByteBuffer response = ByteBuffer.allocateDirect(body.length + header.length).put(header).put(body);

    @Override
    public void accept(TcpConnection tcpConnection) {
        var input = tcpConnection.getInBuffer();
        input.position(input.limit());
        tcpConnection.write(response.flip());
        tcpConnection.flush();
    }
}
