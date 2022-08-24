package com.github.longdt.netz.http.request;

import com.github.longdt.netz.http.HttpRequestReader;

import java.nio.ByteBuffer;

public class DefaultHttpRequest implements HttpRequest {
    private String method;
    private String uri;
    private String version;
    private String headers;
    private ByteBuffer body;
    private final HttpRequestReader parser;

    public DefaultHttpRequest(HttpRequestReader parser) {
        this.parser = parser;
    }

    public void reset() {
        method = null;
        uri = null;
        version = null;
        headers = null;
        body = null;
    }

    @Override
    public String getMethod() {
        return method != null ? method : (method = parser.getMethod());
    }

    @Override
    public String getUri() {
        return uri != null ? uri : (uri = parser.getUri());
    }

    @Override
    public String getVersion() {
        return version != null ? version : (version = parser.getVersion());
    }

    @Override
    public String getHeaders() {
        return headers != null ? headers : (headers = parser.getHeaders());
    }

    @Override
    public ByteBuffer getBody() {
        return body != null ? body : (body = parser.getBody());
    }
}
