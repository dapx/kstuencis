package kstuencis.socket

import kstuencis.core.MessageParser
import kstuencis.core.Store

suspend fun <T> Socket.listen(messageParser: MessageParser<T>, store: Store<T>) {
    SocketListener(
        messageParser = messageParser,
        store = store,
        socket = this
    ).listen()
}