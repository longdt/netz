package com.github.longdt.netz.socket;

import com.github.longdt.netz.socket.concurrent.IOThread;

import java.io.IOException;

public class TcpServer {
    private final int port;

    TcpServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors() * 2; ++i) {
            Thread t = new IOThread(new EventLoop(port));
            t.start();
        }
    }

    public void close() {

    }

    public static Builder newBuilder() {
        return null;
    }

    public interface Builder {
        Builder connectionHandler();

        TcpServer build();
    }
}
