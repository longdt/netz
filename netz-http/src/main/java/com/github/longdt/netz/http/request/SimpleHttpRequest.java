package com.github.longdt.netz.http.request;


import java.net.http.HttpHeaders;
import java.nio.ByteBuffer;

public class SimpleHttpRequest implements HttpRequest {
    private String method;
    private String uri;
    private String version;
    private HttpHeaders headers;
    private ByteBuffer body;

    public SimpleHttpRequest() {
    }

    public void reset() {
        method = null;
        uri = null;
        version = null;
        headers = null;
        body = null;
    }

    public SimpleHttpRequest setMethod(String method) {
        this.method = method;
        return this;
    }

    public SimpleHttpRequest setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public SimpleHttpRequest setVersion(String version) {
        this.version = version;
        return this;
    }

    public SimpleHttpRequest setHeaders(HttpHeaders headers) {
        this.headers = headers;
        return this;
    }

    public SimpleHttpRequest setBody(ByteBuffer body) {
        this.body = body;
        return this;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    @Override
    public ByteBuffer getBody() {
        return body;
    }
}
