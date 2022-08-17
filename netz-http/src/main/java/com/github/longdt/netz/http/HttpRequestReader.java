package com.github.longdt.netz.http;

import com.github.longdt.netz.http.request.HttpRequestImpl;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpRequestReader {
    private static final byte SPACE = ' ';
    private static final byte NEW_LINE = '\n';
    private final byte[] tmpBuffer;
    private int offset;
    private int start;

    public HttpRequestReader(byte[] tmpBuffer) {
        this.tmpBuffer = tmpBuffer;
    }

    public int read(ByteBuffer buffer, HttpRequestImpl httpRequest) {
        int end;
        if (httpRequest.getMethod() == null) {
            end = scan(buffer, SPACE, offset);
            if (end > 0) {
                var method = getString(buffer, start, end - start, StandardCharsets.US_ASCII);
                httpRequest.setMethod(method);
                offset = start = ++end;
            } else {
                offset = buffer.limit();
                return -1;
            }
        }
        if (httpRequest.getUri() == null) {
            end = scan(buffer, SPACE, offset);
            if (end > 0) {
                var uri = getString(buffer, start, end - start, StandardCharsets.US_ASCII);
                httpRequest.setUri(uri);
                offset = start = ++end;
            } else {
                offset = buffer.limit();
                return -1;
            }
        }
        if (httpRequest.getVersion() == null) {
            end = scan(buffer, NEW_LINE, offset);
            if (end > 0) {
                var length = buffer.get(end - 1) == '\r' ? end - start - 1 : end - start;
                var version = getString(buffer, start, length, StandardCharsets.US_ASCII);
                httpRequest.setVersion(version);
                offset = start = ++end;
            } else {
                offset = buffer.limit();
                return -1;
            }
        }
        return scanEmptyLine(buffer);
    }

    private int scan(ByteBuffer buffer, byte value, int from) {
        for (; from < buffer.limit(); ++from) {
            if (buffer.get(from) == value) {
                return from;
            }
        }
        return -1;
    }

    private int scanEmptyLine(ByteBuffer buffer) {
        for (; offset < buffer.limit(); ++offset) {
            if (buffer.get(offset - 3) == '\r' && buffer.get(offset - 2) == '\n'
                    && buffer.get(offset - 1) == '\r' && buffer.get(offset) == '\n') {
                return offset;
            }
        }
        return -1;
    }

    private String getString(ByteBuffer buffer, int from, int length) {
        return getString(buffer, from, length, StandardCharsets.UTF_8);
    }

    private String getString(ByteBuffer buffer, int from, int length, Charset charset) {
        buffer.get(from, tmpBuffer, 0, length);
        return new String(tmpBuffer, 0, length, charset);
    }

    public void reset() {
        offset = 0;
        start = 0;
    }
}
