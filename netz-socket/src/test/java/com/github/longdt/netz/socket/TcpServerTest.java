package com.github.longdt.netz.socket;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TcpServerTest {
    public static void main(String[] args) throws IOException {
        new TcpServer(8080).start();
    }
}
