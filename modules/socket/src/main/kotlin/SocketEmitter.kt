package kstuencis.socket

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kstuencis.core.Emitter
import kstuencis.core.Event
import kstuencis.core.MessageSerializer
import kstuencis.core.Validable

class SocketEmitter<T>(
    private val messageSerializer: MessageSerializer<T>,
    private val socket: Socket
) : Emitter<T>, Validable {
    override suspend fun emit(event: Event<T>) = withContext(Dispatchers.IO) {
        socket.emitMessage { messageSerializer.serialize(event) }
    }

    override fun isInvalid(): Boolean = socket.isClosed()

    override fun isValid(): Boolean = !isInvalid()
}