package kstuencis.socket

import kstuencis.socket.internal.NetSocket
import kstuencis.socket.internal.listenMessages
import kstuencis.socket.internal.acceptAndExecute
import kstuencis.socket.internal.writeTextToOutputStream
import java.net.ServerSocket

typealias NetServerSocket = ServerSocket

private class SocketServerWrapper(private val serverSocket: NetServerSocket) : SocketServer {
    override suspend fun onConnect(block: suspend Socket.() -> Unit) = serverSocket.acceptAndExecute {
        SocketWrapper(this).block()
    }
}

private class SocketWrapper(private val socket: NetSocket) : Socket {
    private val stopMessage = "bye"

    override suspend fun onMessage(block: suspend (message: String) -> Unit): Unit = socket.listenMessages(
        stopMessage,
        block
    )

    override suspend fun emitMessage(block: suspend () -> String) = socket.writeTextToOutputStream(block)

    override fun isClosed(): Boolean = socket.isClosed
}

suspend fun openSocket(port: Int = 9999, block: suspend SocketServer.() -> Unit) {
    SocketServerWrapper(NetServerSocket(port)).block()
}