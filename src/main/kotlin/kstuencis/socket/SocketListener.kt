package kstuencis.socket

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kstuencis.Listener
import kstuencis.MessageParser
import kstuencis.Store

class SocketListener<T>(
    private val messageParser: MessageParser<T>,
    private val store: Store<T>,
    private val socket: Socket
) : Listener {
    override suspend fun listen(): Unit = withContext(Dispatchers.IO) {
        launch {
            socket.onMessage { message ->
                dispatch(message)
            }
        }
    }

    private suspend fun dispatch(message: String) {
        messageParser.parse(message).let { store.dispatch(it) }
    }
}