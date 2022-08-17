package com.github.longdt.netz.http;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpServerTest {
    public static void main(String[] args) throws IOException {
        new HttpServer(8080).start();
    }

}
