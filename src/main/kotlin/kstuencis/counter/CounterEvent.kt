package kstuencis.counter

import kstuencis.core.Event

sealed class CounterEvent(override val payload: Int): Event<Int> {
    data class Increment(override val payload: Int) : CounterEvent(payload)
    data class Decrement(override val payload: Int) : CounterEvent(payload)
    data class Unknown(override val payload: Int = 0, val rawPayload: String): CounterEvent(payload)
}