package kstuencis.socket

import kstuencis.socket.internal.listenMessages
import kstuencis.socket.internal.acceptAndExecute
import java.net.ServerSocket

private class SocketServerWrapper(private val serverSocket: ServerSocket) : SocketServer {
    override suspend fun onConnect(block: suspend Socket.() -> Unit) = serverSocket.acceptAndExecute {
        SocketWrapper(this).block()
    }
}

private class SocketWrapper(private val socket: java.net.Socket) : Socket {
    private val stopMessage = "bye"

    override suspend fun onMessage(block: suspend (message: String) -> Unit): Unit = socket.listenMessages(
        stopMessage,
        block
    )
}

suspend fun openSocket(port: Int = 9999, block: suspend SocketServer.() -> Unit) {
    SocketServerWrapper(ServerSocket(port)).block()
}