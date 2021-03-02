package kstuencis.counter

import kstuencis.Event

sealed class CounterEmitEvent : Event<Int> {
    data class UpdateState(override val payload: Int) : CounterEmitEvent()
}