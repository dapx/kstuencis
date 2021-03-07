package kstuencis.console

import kstuencis.core.Emitter
import kstuencis.core.Event
import kstuencis.core.MessageSerializer

class ConsoleEmitter<T>(private val messageSerializer: MessageSerializer<T>) : Emitter<T> {
    override suspend fun emit(event: Event<T>) {
        messageSerializer.serialize(event).let(::println)
    }
}