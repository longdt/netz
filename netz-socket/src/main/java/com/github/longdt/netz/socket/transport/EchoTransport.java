package com.github.longdt.netz.socket.transport;

import com.github.longdt.netz.socket.TcpConnection;

import java.util.function.Consumer;

public class EchoTransport implements Consumer<TcpConnection> {

    @Override
    public void accept(TcpConnection tcpConnection) {
        tcpConnection.write(tcpConnection.getInBuffer());
    }
}
