package kstuencis.socket

interface Socket {
    suspend fun onMessage(block: suspend (message: String) -> Unit)
    suspend fun emitMessage(block: suspend () -> String)
    fun isClosed(): Boolean
}
