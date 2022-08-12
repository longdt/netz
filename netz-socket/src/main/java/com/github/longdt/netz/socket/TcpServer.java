package com.github.longdt.netz.socket;

import com.github.longdt.netz.socket.concurrent.IOThread;

import java.io.IOException;

public class TcpServer {
    private final int port;

    public TcpServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
//        Thread t = new IOThread(new EventLoop(port));
//        t.start();
        for (int i = 0; i < Runtime.getRuntime().availableProcessors() * 2; ++i) {
            Thread t = new IOThread(new EventLoop(port));
            t.start();
        }
    }

    public void close() {

    }
}
