package kstuencis

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConsoleListener<T>(private val parser: MessageParser<T>, private val store: Store<T>) : Listener {
    override suspend fun listen() = withContext(Dispatchers.Default) {
        while (true) try {
            readInput()
                .let { parser.parse(it) }
                .let { store.dispatch(it) }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private suspend fun readInput() = withContext(Dispatchers.IO) {
        checkNotNull(readLine()) { "You must type something" }
    }
}