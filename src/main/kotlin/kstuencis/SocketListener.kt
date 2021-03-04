package kstuencis

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ServerSocket
import java.net.Socket

class SocketListener<T>(
    private val messageParser: MessageParser<T>,
    private val store: Store<T>,
    private val port: Int = 9999
) : Listener {
    override suspend fun listen() = withContext(Dispatchers.Default) {
        ServerSocket(port).use { server ->
            println("Server running on port $port")
            while (true) try {
                val socket = server.accept()
                launch {
                    socket.use { socket -> socket.listenMessages() }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private suspend fun Socket.listenMessages(stopMessage: String = "bye") = withContext(Dispatchers.IO) {
        do {
            if (isClosed) break
            val message = readTextFromInputStream()
            messageParser.parse(message).let { store.dispatch(it) }
        } while (message != stopMessage)
    }

    private suspend fun Socket.readTextFromInputStream(): String = withContext(Dispatchers.IO) {
        getInputStream().reader().use { it.readLines().first() }
    }
}