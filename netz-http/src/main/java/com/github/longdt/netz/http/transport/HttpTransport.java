package com.github.longdt.netz.http.transport;

import com.github.longdt.netz.http.HttpConnection;
import com.github.longdt.netz.http.request.DefaultHttpRequest;
import com.github.longdt.netz.socket.TcpConnection;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class HttpTransport implements Consumer<TcpConnection> {
    private static final byte[] body = "Hello World".getBytes(StandardCharsets.UTF_8);
    private static final byte[] header = ("HTTP/1.1 200 OK\r\nConnection: Keep-Alive\r\nContent-Length: " + body.length + "\r\n\r\n").getBytes(StandardCharsets.UTF_8);
    private final ByteBuffer response = ByteBuffer.allocateDirect(body.length + header.length).put(header).put(body);

    @Override
    public void accept(TcpConnection tcpConnection) {
        var httpConn = (HttpConnection) tcpConnection;
        var requestReader = httpConn.getRequestReader();
        var offset = requestReader.read();
        if (offset > 0) {
            var httpRequest = (DefaultHttpRequest) httpConn.getRequest();
//            System.out.println(httpRequest.getMethod() + " " + httpRequest.getUri() + " " + httpRequest.getVersion());
//            System.out.println(httpRequest.getHeaders());
            tcpConnection.write(response.flip());
            httpRequest.reset();
            requestReader.reset();
            tcpConnection.getInBuffer().position(offset);
        }
    }
}
