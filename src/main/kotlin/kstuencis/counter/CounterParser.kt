package kstuencis.counter

import kstuencis.MessageParser

object CounterParser : MessageParser<Int> {
    override suspend fun parse(text: String): CounterEvent {
        val (type, payload) = extractData(text)

        return when (type) {
            "increment" -> payload.let(Integer::parseInt).let(CounterEvent::Increment)
            "decrement" -> payload.let(Integer::parseInt).let(CounterEvent::Decrement)
            else -> CounterEvent.Unknown(0, rawPayload = payload)
        }
    }

    private fun extractData(text: String): Pair<String, String> {
        return try {
            val (type, payload) = text.split(":")
            Pair(type, payload)
        } catch (ex: Exception) {
            Pair("unknown", "input: $text")
        }
    }
}