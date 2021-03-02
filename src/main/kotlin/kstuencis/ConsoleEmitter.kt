package kstuencis

import kstuencis.counter.CounterEmitEvent

class ConsoleEmitter<T>(private val messageSerializer: MessageSerializer<T>) : Emitter<T> {
    override suspend fun emit(event: Event<T>) {
        messageSerializer.serialize(event).let(::println)
    }
}