package kstuencis.socket

interface SocketServer {
    suspend fun onConnect(block: suspend Socket.() -> Unit)
}
