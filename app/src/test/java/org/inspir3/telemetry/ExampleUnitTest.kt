package org.inspir3.telemetry

import org.inspir3.common.DateTime
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `DateTime-fromTsToHHmm() should work`() {
        val ts = 1722032842L
        assertEquals("00:27", DateTime.fromTsToHHmm(ts))
    }
}