package com.github.longdt.netz.socket;

import com.github.longdt.netz.socket.transport.EchoTransport;

import java.io.IOException;

class TcpServerTest {
    public static void main(String[] args) throws IOException {
        TcpServer.newBuilder()
                .port(8080)
                .requestHandlerFactory(EchoTransport::new)
                .build()
                .start();
    }
}
