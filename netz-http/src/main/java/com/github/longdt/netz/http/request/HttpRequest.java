package com.github.longdt.netz.http.request;

import java.nio.ByteBuffer;

public interface HttpRequest {
    String getMethod();

    String getUri();

    String getVersion();

    String getHeaders();

    ByteBuffer getBody();
}
