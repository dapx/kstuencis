package kstuencis

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmitterSubject<T>(private val emitters: MutableList<Emitter<T>> = mutableListOf()) {
    fun register(emitter: Emitter<T>) {
        emitters.add(emitter)
    }

    suspend fun notify(event: Event<T>) = withContext(Dispatchers.IO) {
        // The list can be changed at the same time that is iterating,
        // to fix that we copy the list
        emitters.toTypedArray().forEach {
            when (it) {
                is Validable -> if (it.isInvalid()) emitters.remove(it) else it emit event
                else -> it emit event
            }
        }
    }
}