package kstuencis.counter

import kotlinx.coroutines.runBlocking
import kstuencis.counter.CounterEvent.Increment
import kotlin.test.Test
import kotlin.test.assertEquals

class CounterStateTest {
    @Test
    fun `Should change the state on dispatch`() = runBlocking {
        val counter = CounterState()

        counter.dispatch(Increment(payload = 5))
        assertEquals(expected = 5, actual = counter.compute())

        counter.dispatch(Increment(payload = 3))
        assertEquals(expected = 8, actual = counter.compute())
    }
}