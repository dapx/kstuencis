package kstuencis.socket

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kstuencis.core.Listener
import kstuencis.core.MessageParser
import kstuencis.core.Store

class SocketListener<T>(
    private val messageParser: MessageParser<T>,
    private val store: Store<T>,
    private val socket: Socket
) : Listener {
    override suspend fun listen() {
        withContext(Dispatchers.IO) {
            launch {
                socket.onMessage { message ->
                    dispatch(message)
                }
            }
        }
    }

    private suspend fun dispatch(message: String) {
        messageParser.parse(message).let { store.dispatch(it) }
    }
}