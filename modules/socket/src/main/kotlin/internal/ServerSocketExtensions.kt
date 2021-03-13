package kstuencis.socket.internal

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException

internal suspend fun ServerSocket.waitForConnectionAndExecute(
    block: suspend Socket.() -> Unit
) = withContext(Dispatchers.IO) {
    use { server ->
        while (!isClosed) try {
            val socket = server.acceptNotThrowingWhenUnexpectedClosed()
            socket?.block()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}

private suspend fun ServerSocket.acceptNotThrowingWhenUnexpectedClosed(): Socket? = withContext(Dispatchers.IO) {
    try {
        return@withContext accept()
    } catch (ex: SocketException) {
        when (isClosed) {
            false -> throw ex
            else -> null
        }
    }
}