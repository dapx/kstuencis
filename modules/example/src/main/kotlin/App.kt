package kstuencis

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kstuencis.console.ConsoleEmitter
import kstuencis.console.ConsoleListener
import kstuencis.core.EmitterSubject
import kstuencis.counter.CounterEmitEvent
import kstuencis.counter.CounterParser
import kstuencis.counter.CounterSerializer
import kstuencis.counter.CounterState
import kstuencis.socket.SocketEmitter
import kstuencis.socket.listen
import kstuencis.socket.openSocket

fun main() = runBlocking {
    val counterConsoleListener = ConsoleListener(
        parser = CounterParser,
        store = CounterState
    )

    val counterConsoleEmitter = ConsoleEmitter(CounterSerializer)
    val emitterSubject = EmitterSubject<Int>()
    emitterSubject.register(counterConsoleEmitter)


    launch {
        openSocket {
            onConnect {
                listen(messageParser = CounterParser, store = CounterState)
                val counterSocketEmitter = SocketEmitter(CounterSerializer, this@onConnect)
                emitterSubject.register(counterSocketEmitter)
            }
        }
    }


    launch { counterConsoleListener.listen() }

    // It will work as a game loop with some tick
    // but at this moment It is a basic delay after computing the state.
    loop(1000.ms) {
        val newState = CounterState.compute()
        emitterSubject.notify(CounterEmitEvent.UpdateState(newState))
    }
}

// TODO - Gracefully shutdown
suspend fun loop(interval: Long = 0, block: suspend () -> Any) = withContext(Dispatchers.Default) {
    while (true) {
        block()
        delay(interval)
    }
}

val Int.ms get() = this * 1L