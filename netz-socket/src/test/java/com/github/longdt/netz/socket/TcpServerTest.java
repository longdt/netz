package com.github.longdt.netz.socket;

import com.github.longdt.netz.socket.transport.SimpleHttpTransport;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TcpServerTest {
    public static void main(String[] args) throws IOException {
        TcpServer.newBuilder()
                .port(8080)
                .requestHandlerFactory(SimpleHttpTransport::new)
                .build()
                .start();
    }
}
