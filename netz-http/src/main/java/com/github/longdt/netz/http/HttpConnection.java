package com.github.longdt.netz.http;

import com.github.longdt.netz.http.request.HttpRequest;
import com.github.longdt.netz.http.request.HttpRequestImpl;
import com.github.longdt.netz.http.response.HttpResponse;
import com.github.longdt.netz.socket.LocalProvider;
import com.github.longdt.netz.socket.TcpConnection;

import java.nio.channels.SocketChannel;

public class HttpConnection extends TcpConnection {
    private final HttpRequest request;
    private final HttpResponse response;
    private final HttpRequestReader requestReader;
    protected HttpConnection(SocketChannel socketChannel, LocalProvider localProvider) {
        super(socketChannel, localProvider);
        request = new HttpRequestImpl();
        response = new HttpResponse();
        requestReader = new HttpRequestReader(localProvider.getTmpBuffer());
    }

    public HttpRequest getRequest() {
        return request;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public HttpRequestReader getRequestReader() {
        return requestReader;
    }
}
