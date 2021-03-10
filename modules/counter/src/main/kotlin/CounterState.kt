package kstuencis.counter

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kstuencis.core.Event
import kstuencis.core.Store

object CounterState : Store<Int> {
    private val mutableState = MutableStateFlow(0)

    override suspend fun dispatch(event: Event<Int>) {
        when (event) {
            is CounterEvent.Increment -> mutableState.value += event.payload
            is CounterEvent.Decrement -> mutableState.value -= event.payload
            is CounterEvent.Unknown -> println("Unknown ${event.rawPayload}")
        }
    }

    override fun compute(): Int {
        return mutableState.value
    }
}