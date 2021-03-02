package kstuencis

interface Emitter<T> {
    suspend fun emit(event: Event<T>)
}