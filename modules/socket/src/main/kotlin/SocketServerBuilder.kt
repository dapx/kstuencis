package kstuencis.socket

import kstuencis.socket.internal.waitForConnectionAndExecute
import kstuencis.socket.internal.listenMessages
import kstuencis.socket.internal.writeTextToOutputStream
import java.net.ServerSocket as NetServerSocket
import java.net.Socket as NetSocket

private class SocketServerWrapper(private val serverSocket: NetServerSocket) : SocketServer {
    override suspend fun onConnect(block: suspend Socket.() -> Unit) = serverSocket.waitForConnectionAndExecute {
        SocketWrapper(this).block()
    }

    override fun close() = serverSocket.close()
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

suspend fun openSocketServer(port: Int = 9999, block: suspend SocketServer.() -> Unit) {
    SocketServerWrapper(NetServerSocket(port)).block()
}