package kstuencis

interface MessageSerializer<T> {
    suspend fun serialize(event: Event<T>): String
}