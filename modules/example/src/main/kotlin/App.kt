package kstuencis

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kstuencis.common.loop
import kstuencis.console.ConsoleEmitter
import kstuencis.console.ConsoleListener
import kstuencis.core.EmitterSubject
import kstuencis.counter.CounterEmitEvent
import kstuencis.counter.CounterParser
import kstuencis.counter.CounterSerializer
import kstuencis.counter.CounterState
import kstuencis.socket.SocketEmitter
import kstuencis.socket.listen
import kstuencis.socket.openSocketServer

fun main() = runBlocking {
    val counterState = CounterState()
    val counterConsoleListener = ConsoleListener(
        parser = CounterParser,
        store = counterState
    )

    val counterConsoleEmitter = ConsoleEmitter(CounterSerializer)
    val emitterSubject = EmitterSubject<Int>()
    emitterSubject.register(counterConsoleEmitter)

    launch {
        openSocketServer {
            onConnect {
                val counterSocketEmitter = SocketEmitter(
                    messageSerializer = CounterSerializer,
                    socket = this@onConnect
                )
                emitterSubject.register(counterSocketEmitter)
                listen(messageParser = CounterParser, store = counterState)
            }
        }
    }

    launch { counterConsoleListener.listen() }

    // It will work as a game loop with some tick
    // but at this moment It is a basic delay after computing the state.
    loop(1000.ms) {
        counterState.compute()
            .let(CounterEmitEvent::UpdateState)
            .let { emitterSubject.notify(it) }
    }
}

val Int.ms get() = this * 1L