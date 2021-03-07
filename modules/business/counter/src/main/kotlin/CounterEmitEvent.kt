package kstuencis.counter

import kstuencis.core.Event

sealed class CounterEmitEvent : Event<Int> {
    data class UpdateState(override val payload: Int) : CounterEmitEvent()
}