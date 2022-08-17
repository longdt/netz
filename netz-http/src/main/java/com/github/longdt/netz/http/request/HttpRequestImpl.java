package com.github.longdt.netz.http.request;


import java.net.http.HttpHeaders;
import java.nio.ByteBuffer;

public class HttpRequestImpl implements HttpRequest {
    private String method;
    private String uri;
    private String version;
    private HttpHeaders headers;
    private ByteBuffer body;
    private State state;

    public enum State {
        READING_HEADERS,
        READING_BODY,
        UPGRADED
    }

    public void reset() {
        method = null;
        uri = null;
        version = null;
        headers = null;
        body = null;
        state = State.READING_HEADERS;
    }

    public HttpRequestImpl setMethod(String method) {
        this.method = method;
        return this;
    }

    public HttpRequestImpl setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public HttpRequestImpl setVersion(String version) {
        this.version = version;
        return this;
    }

    public HttpRequestImpl setHeaders(HttpHeaders headers) {
        this.headers = headers;
        return this;
    }

    public HttpRequestImpl setBody(ByteBuffer body) {
        this.body = body;
        return this;
    }

    public HttpRequestImpl setState(State state) {
        this.state = state;
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

    public State getState() {
        return state;
    }
}
