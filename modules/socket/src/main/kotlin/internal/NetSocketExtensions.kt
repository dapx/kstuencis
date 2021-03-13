package kstuencis.socket.internal

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketException
import java.net.Socket as NetSocket

internal suspend fun NetSocket.listenMessages(
    stopMessage: String = "bye",
    block: suspend (message: String) -> Unit
) = withContext(Dispatchers.IO) {
    use {
        do {
            if (isClosed) break
            val message = readLineFromInputStream(it)
            when (message) { is String -> block(message) }
        } while (message != stopMessage)
    }
}

internal suspend fun readLineFromInputStream(socket: NetSocket): String? = withContext(Dispatchers.IO) {
    val inputStream = try { socket.getInputStream() } catch (ex: Exception) { return@withContext null }
    inputStream.bufferedReader().readLine()?.trim()
}

internal suspend fun NetSocket.writeTextToOutputStream(block: suspend () -> String) = withContext(Dispatchers.IO) {
    try {
        getOutputStream().writer().run {
            write("${block()}\n")
            flush()
        }
    } catch (ex: SocketException) {
        this@writeTextToOutputStream.close()
    }
}
