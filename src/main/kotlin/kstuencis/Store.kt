package kstuencis

/**
 * This interface can be implemented as a list of events that can be computed
 * But to work on an eager and mutable way, the events can be pre-computed on dispatching
 * and the value returned on compute called.
 */
interface Store<T> {
    suspend fun dispatch(value: Event<T>)
    fun compute(): T
}