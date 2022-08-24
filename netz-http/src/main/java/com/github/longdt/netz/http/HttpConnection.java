package com.github.longdt.netz.http;

import com.github.longdt.netz.http.request.DefaultHttpRequest;
import com.github.longdt.netz.http.request.HttpRequest;
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
        requestReader = new HttpRequestReaderImpl(getInBuffer(), localProvider.getTmpBuffer());
        request = new DefaultHttpRequest(requestReader);
        response = new HttpResponse();
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
