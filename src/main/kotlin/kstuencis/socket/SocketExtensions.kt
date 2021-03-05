package kstuencis.socket

import kstuencis.MessageParser
import kstuencis.Store

suspend fun <T> Socket.listen(messageParser: MessageParser<T>, store: Store<T>) {
    SocketListener(
        messageParser = messageParser,
        store = store,
        socket = this
    ).listen()
}