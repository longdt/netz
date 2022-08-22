package com.github.longdt.netz.http;

import java.nio.ByteBuffer;

public interface HttpParser {
    boolean parse(ByteBuffer buffer);
}
