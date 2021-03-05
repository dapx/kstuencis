package kstuencis.console

import kstuencis.Emitter
import kstuencis.Event
import kstuencis.MessageSerializer

class ConsoleEmitter<T>(private val messageSerializer: MessageSerializer<T>) : Emitter<T> {
    override suspend fun emit(event: Event<T>) {
        messageSerializer.serialize(event).let(::println)
    }
}