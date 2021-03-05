package kstuencis.socket

interface Socket {
    suspend fun onMessage(block: suspend (message: String) -> Unit)
}
