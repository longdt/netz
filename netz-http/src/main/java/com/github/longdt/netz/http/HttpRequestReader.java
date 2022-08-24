package com.github.longdt.netz.http;

import java.nio.ByteBuffer;

public interface HttpRequestReader {
    int read();

    void reset();

    String getMethod();

    String getUri();

    String getVersion();

    String getHeaders();

    ByteBuffer getBody();
}
