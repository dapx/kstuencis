package kstuencis.core

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class EmitterSubjectTest {
    @Test
    fun `Should call emitter on notify`() = runBlocking {
        var actualState = "Not changed"
        val expectState = "It works"
        val emitterSubject = EmitterSubject<String>()

        val emitter = object : Emitter<String> {
            override suspend fun emit(event: Event<String>) {
                actualState = event.payload
            }
        }
        emitterSubject.register(emitter)

        emitterSubject.notify(object : Event<String> {
            override val payload: String get() = expectState
        })

        assertEquals(expectState, actualState)
    }
}