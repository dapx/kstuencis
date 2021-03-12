package kstuencis.counter

import kotlinx.coroutines.runBlocking
import kstuencis.counter.CounterEvent.Increment
import kstuencis.counter.CounterEvent.Decrement
import kotlin.test.Test
import kotlin.test.assertEquals

class CounterStateTest {
    @Test
    fun `Should change the state when dispatching event`() = runBlocking {
        val counter = CounterState()

        counter.dispatch(Increment(5))
        assertEquals(expected = 5, actual = counter.compute())

        counter.dispatch(Decrement(2))
        assertEquals(expected = 3, actual = counter.compute())

        counter.dispatch(Increment(3))
        assertEquals(expected = 6, actual = counter.compute())
    }
}