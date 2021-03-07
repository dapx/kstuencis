package kstuencis.core

interface MessageSerializer<T> {
    suspend fun serialize(event: Event<T>): String
}