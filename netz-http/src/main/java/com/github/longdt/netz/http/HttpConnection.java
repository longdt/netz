package com.github.longdt.netz.http;

import com.github.longdt.netz.http.request.HttpRequest;
import com.github.longdt.netz.http.response.HttpResponse;
import com.github.longdt.netz.socket.TcpConnection;
import com.github.longdt.netz.socket.pool.Pool;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class HttpConnection extends TcpConnection {
    private final HttpRequest request;
    private final HttpResponse response;
    protected HttpConnection(SocketChannel socketChannel, Pool<ByteBuffer> bufferPool) {
        super(socketChannel, bufferPool);
        request = new HttpRequest();
        response = new HttpResponse();
    }

    public HttpRequest getRequest() {
        return request;
    }

    public HttpResponse getResponse() {
        return response;
    }
}
