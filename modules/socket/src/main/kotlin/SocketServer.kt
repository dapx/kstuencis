package kstuencis.socket

interface SocketServer : AutoCloseable {
    suspend fun onConnect(block: suspend Socket.() -> Unit)
}
