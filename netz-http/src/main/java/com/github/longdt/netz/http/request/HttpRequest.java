package com.github.longdt.netz.http.request;


import java.net.InetAddress;
import java.net.http.HttpHeaders;
import java.nio.ByteBuffer;

public class HttpRequest {
    private String method;
    private String uri;
    private String version;
    private HttpHeaders headers;
    private ByteBuffer body;

    private InetAddress remoteAddress;

    public HttpRequest() {

    }

    public HttpRequest(String method, String uri, String version, HttpHeaders headers, ByteBuffer body, InetAddress remoteAddress) {
        this.method = method;
        this.uri = uri;
        this.version = version;
        this.headers = headers;
        this.body = body;
        this.remoteAddress = remoteAddress;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getVersion() {
        return version;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public ByteBuffer getBody() {
        return body;
    }

    public InetAddress getRemoteAddress() {
        return remoteAddress;
    }
}
