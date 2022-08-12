package com.github.longdt.netz.socket.transport;

import com.github.longdt.netz.socket.TcpConnection;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class SimpleHttpTransport {
    private static final byte[] body = "Hello World".getBytes(StandardCharsets.UTF_8);
    private static final byte[] header = ("HTTP/1.1 200 OK\r\nContent-Length: " + body.length + "\r\n\r\n").getBytes(StandardCharsets.UTF_8);
    private static final ByteBuffer response = ByteBuffer.allocateDirect(body.length + header.length).put(header).put(body);

    public static void handleHttpRequest(TcpConnection connection, ByteBuffer buffer) {
        int offset = buffer.position() + 4;
        for (; offset < buffer.limit(); ++offset) {
            if (buffer.get(offset - 4) == '\r' && buffer.get(offset - 3) == '\n' && buffer.get(offset - 2) == '\r' && buffer.get(offset - 1) == '\n'
            ) {
                break;
            }
        }
        buffer.position(offset);
        connection.write(response.flip());
    }
}
