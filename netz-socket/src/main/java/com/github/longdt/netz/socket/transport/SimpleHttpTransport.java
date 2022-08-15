package com.github.longdt.netz.socket.transport;

import com.github.longdt.netz.socket.TcpConnection;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.function.BiConsumer;

public class SimpleHttpTransport implements BiConsumer<TcpConnection, ByteBuffer> {
    private static final byte[] body = "Hello World".getBytes(StandardCharsets.UTF_8);
    private static final byte[] header = ("HTTP/1.1 200 OK\r\nContent-Length: " + body.length + "\r\n\r\n").getBytes(StandardCharsets.UTF_8);
    private final ByteBuffer response = ByteBuffer.allocateDirect(body.length + header.length).put(header).put(body);

    @Override
    public void accept(TcpConnection tcpConnection, ByteBuffer buffer) {
        var request = parseHttpRequest(buffer);
        if (request != null) {
            tcpConnection.write(response.flip());
        }
    }

    private HttpRequest parseHttpRequest(ByteBuffer buffer) {
        int offset = buffer.position() + 3;
        var requestEnd = false;
        for (; offset < buffer.limit(); ++offset) {
            if (buffer.get(offset - 3) == '\r' && buffer.get(offset - 2) == '\n'
                    && buffer.get(offset - 1) == '\r' && buffer.get(offset) == '\n') {
                requestEnd = true;
                break;
            }
        }
        if (!requestEnd) {
            return null;
        }
//        buffer.mark();
        buffer.position(++offset);
        return new HttpRequest(null, null, null, null, null, null);
    }

    private String readStringUntil(ByteBuffer buffer, byte stopAt) {
//        buffer.asCharBuffer().
        return null;
    }
}
