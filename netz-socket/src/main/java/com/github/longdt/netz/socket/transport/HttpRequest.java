package com.github.longdt.netz.socket.transport;


import java.net.InetAddress;
import java.net.http.HttpHeaders;

public class HttpRequest {
    private final String method;
    private final String uri;
    private final String version;
    private final HttpHeaders headers;
    private final byte[] body;

    private final InetAddress remoteAddress;

    public HttpRequest(String method, String uri, String version, HttpHeaders headers, byte[] body, InetAddress remoteAddress) {
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

    public byte[] getBody() {
        return body;
    }

    public InetAddress getRemoteAddress() {
        return remoteAddress;
    }
}
