package kstuencis.counter

import kstuencis.core.Event
import kstuencis.core.Store

object CounterState : Store<Int> {
    // TODO - Implement lock or some atomic alternative
    private var value = 0

    override suspend fun dispatch(event: Event<Int>) {
        when (event) {
            is CounterEvent.Increment -> value += event.payload
            is CounterEvent.Decrement -> value -= event.payload
            is CounterEvent.Unknown -> println("Unknown ${event.rawPayload}")
        }
    }

    override fun compute(): Int {
        return value
    }
}