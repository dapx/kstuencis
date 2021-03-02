/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package kstuencis

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kstuencis.counter.CounterEmitEvent
import kstuencis.counter.CounterParser
import kstuencis.counter.CounterSerializer
import kstuencis.counter.CounterState

fun main(args: Array<String>) {
    val counterListener = ConsoleListener(CounterParser, CounterState)
    val counterEmitter = ConsoleEmitter(CounterSerializer)

    runBlocking {
        launch {
            counterListener.listen()
        }

        // It will work as a game loop with some tick
        // but at this moment It is a basic delay after computing the state.
        loop(1000.ms) {
            val newState = CounterState.compute()
            counterEmitter.emit(CounterEmitEvent.UpdateState(newState))
        }
    }
}

// TODO - Gracefully shutdown
suspend fun loop(interval: Long = 0, block: suspend () -> Any) = withContext(Dispatchers.Default) {
    while(true) {
        block()
        delay(interval)
    }
}

val Int.ms get() = this * 1L