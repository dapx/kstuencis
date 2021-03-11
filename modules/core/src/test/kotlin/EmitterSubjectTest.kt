package kstuencis.core

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EmitterSubjectTest {
    @Test
    fun `Should call all emitters on notify`() = runBlocking {
        var isEmitterCalled = false
        var isOtherEmitterCalled = false
        val emitterSubject = EmitterSubject<Boolean>()

        val emitter = object : Emitter<Boolean> {
            override suspend fun emit(event: Event<Boolean>) {
                isEmitterCalled = event.payload
            }
        }
        emitterSubject.register(emitter)

        val otherEmitter = object : Emitter<Boolean> {
            override suspend fun emit(event: Event<Boolean>) {
                isOtherEmitterCalled = event.payload
            }
        }
        emitterSubject.register(otherEmitter)

        assertFalse(isEmitterCalled)
        assertFalse(isOtherEmitterCalled)

        emitterSubject.notify(object : Event<Boolean> {
            override val payload: Boolean get() = true
        })

        assertTrue(isEmitterCalled)
        assertTrue(isOtherEmitterCalled)
    }

    @Test
    fun `Should not call invalid emitter`() = runBlocking {
        var isEmitterCalled = false
        var isInvalidEmitterCalled = false
        val emitterSubject = EmitterSubject<Boolean>()

        val emitter = object : Emitter<Boolean> {
            override suspend fun emit(event: Event<Boolean>) {
                isEmitterCalled = event.payload
            }
        }
        emitterSubject.register(emitter)

        val invalidEmitter = object : Emitter<Boolean>, Validatable {
            override suspend fun emit(event: Event<Boolean>) {
                isInvalidEmitterCalled = event.payload
            }

            override val isInvalid get(): Boolean = true
            override val isValid get(): Boolean = !isInvalid
        }
        emitterSubject.register(invalidEmitter)

        assertFalse(isEmitterCalled)
        assertFalse(isInvalidEmitterCalled)

        emitterSubject.notify(object : Event<Boolean> {
            override val payload: Boolean get() = true
        })

        assertTrue(isEmitterCalled)
        assertFalse(isInvalidEmitterCalled)
    }
}