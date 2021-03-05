package kstuencis.socket.internal

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.ServerSocket
import java.net.Socket

internal suspend fun ServerSocket.acceptAndExecute(block: suspend Socket.() -> Unit) = withContext(Dispatchers.IO) {
    use { server ->
        while (true) try {
            // TODO: Fix inappropriate blocking method
            val socket = server.accept()
            socket.block()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}