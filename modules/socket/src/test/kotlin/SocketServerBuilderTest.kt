package kstuencis.socket

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kstuencis.common.loop
import kstuencis.core.EmitterSubject
import kstuencis.core.Event
import kstuencis.core.MessageSerializer
import kstuencis.socket.internal.readLineFromInputStream
import kotlin.test.Test
import kotlin.test.assertEquals
import java.net.Socket as NetSocket

class SocketServerBuilderTest {
    @Test
    fun `Should emit messages`() = runBlocking {
        val port = 64234
        val emitterSubject = EmitterSubject<String>()

        openSocketServer(port) {
            launch {
                onConnect {
                    emitterSubject.register(SocketEmitter(
                        messageSerializer = object : MessageSerializer<String> {
                            override suspend fun serialize(event: Event<String>): String {
                                return event.payload
                            }
                        },
                        socket = this@onConnect
                    ))
                }
            }

            val socketClient = NetSocket("localhost", port)
            suspend fun waitForEmitter() = loop(interval = 0, activeWhile = emitterSubject::hasNotEmitters) {}
            waitForEmitter()

            use {
                emitterSubject.notify(TestEvent("testing"))
                assertEquals(expected = "testing", actual = readLineFromInputStream(socketClient))

                emitterSubject.notify(TestEvent("testing two"))
                assertEquals(expected = "testing two", actual = readLineFromInputStream(socketClient))
            }
        }
    }

    private class TestEvent(override val payload: String) : Event<String>
}