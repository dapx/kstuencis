package kstuencis

interface MessageParser<T> {
    suspend fun parse(text: String): Event<T>
}