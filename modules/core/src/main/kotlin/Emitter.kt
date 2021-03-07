package kstuencis.core

interface Emitter<T> {
    suspend infix fun emit(event: Event<T>)
}