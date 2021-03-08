package kstuencis.core

// TODO - Events must guarantee the order cause the state will be dispatched in an concurrent way
interface Event<T> {
    val payload: T
}