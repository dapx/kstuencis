package kstuencis.socket.internal

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.Socket

typealias NetSocket = Socket

internal suspend fun NetSocket.listenMessages(
    stopMessage: String = "bye",
    block: suspend (message: String) -> Unit
) = withContext(Dispatchers.IO) {
    use {
        do {
            if (isClosed) break
            val message = readTextFromInputStream(it)
            block(message)
        } while (message != stopMessage)
    }
}

private suspend fun readTextFromInputStream(socket: NetSocket): String = withContext(Dispatchers.IO) {
    socket.getInputStream().reader().use { it.readLines().first() }
}

suspend fun NetSocket.writeTextToOutputStream(block: suspend () -> String) = withContext(Dispatchers.IO) {
    getOutputStream().writer().use{ it.write(block()) }
}
