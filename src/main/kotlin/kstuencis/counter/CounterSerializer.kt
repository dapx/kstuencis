package kstuencis.counter

import kstuencis.Event
import kstuencis.MessageSerializer

object CounterSerializer : MessageSerializer<Int> {
    override suspend fun serialize(event: Event<Int>): String {
        return when (event) {
            is CounterEmitEvent.UpdateState -> "update:${event.payload}"
            else -> "Unrecognized"
        }
    }

}