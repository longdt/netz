package com.github.longdt.netz.http;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpRequestReaderImpl implements HttpRequestReader {
    private static final byte SPACE = ' ';
    private static final byte NEW_LINE = '\n';
    private final Range method;
    private final Range uri;
    private final Range version;
    private final Range headers;
    private final Range body;
    private final ByteBuffer input;
    private final byte[] tmpBuffer;
    private int offset;

    public HttpRequestReaderImpl(ByteBuffer input, byte[] tmpBuffer) {
        this.input = input;
        this.tmpBuffer = tmpBuffer;
        method = new Range();
        uri = new Range();
        version = new Range();
        headers = new Range();
        body = new Range();
    }

    @Override
    public int read() {
        if (method.getEnd() == 0) {
            if (scan(SPACE)) {
                method.setEnd(offset++);
                uri.setStart(offset);
            } else {
                return -1;
            }
        }
        if (uri.getEnd() == 0) {
            if (scan(SPACE)) {
                uri.setEnd(offset++);
                version.setStart(offset);
            } else {
                return -1;
            }
        }
        if (version.getEnd() == 0) {
            if (scan(NEW_LINE)) {
                version.setEnd(offset - 1);
                headers.setStart(++offset);
            } else {
                return -1;
            }
        }
        if (headers.getEnd() == 0) {
            if (scanEmptyLine()) {
                headers.setEnd(offset - 3);
                return ++offset;
            }
        }
        return -1;
    }

    private boolean scan(byte value) {
        for (; offset < input.limit(); ++offset) {
            if (input.get(offset) == value) {
                return true;
            }
        }
        return false;
    }

    private boolean scanEmptyLine() {
        for (; offset < input.limit(); ++offset) {
            if (input.get(offset - 3) == '\r' && input.get(offset - 2) == '\n'
                    && input.get(offset - 1) == '\r' && input.get(offset) == '\n') {
                return true;
            }
        }
        return false;
    }

    private String getString(ByteBuffer buffer, Range range) {
        return getString(buffer, range, StandardCharsets.UTF_8);
    }

    private String getString(ByteBuffer buffer, Range range, Charset charset) {
        var length = range.length();
        buffer.get(range.getStart(), tmpBuffer, 0, length);
        return new String(tmpBuffer, 0, length, charset);
    }

    @Override
    public void reset() {
        method.reset();
        uri.reset();
        version.reset();
        headers.reset();
        body.reset();
        offset = 0;
    }

    @Override
    public String getMethod() {
        return getString(input, method, StandardCharsets.US_ASCII);
    }

    @Override
    public String getUri() {
        return getString(input, uri, StandardCharsets.US_ASCII);
    }

    @Override
    public String getVersion() {
        return getString(input, version, StandardCharsets.US_ASCII);
    }

    @Override
    public String getHeaders() {
        return getString(input, headers, StandardCharsets.US_ASCII);
    }

    @Override
    public ByteBuffer getBody() {
        return input.slice(body.getStart(), body.length());
    }
}
