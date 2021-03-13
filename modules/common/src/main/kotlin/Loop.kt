package kstuencis.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

// TODO - Gracefully shutdown
suspend fun loop(
    interval: Long = 0,
    activeWhile: () -> Boolean = { true },
    block: suspend () -> Any
) = withContext(Dispatchers.Default) {
    while (activeWhile()) {
        block()
        delay(interval)
    }
}
