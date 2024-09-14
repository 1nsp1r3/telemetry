package org.inspir3.telemetry

import org.inspir3.common.DateTime
import org.inspir3.common.file.Fichier
import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDateTime
import java.time.OffsetDateTime

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun subList() {
        val myList = mutableListOf(1, 2, 3, 4, 5)
        assertEquals(5, myList.size)
        myList.subList(0, 3).clear() //Remove 1, 2 & 3
        assertEquals(2, myList.size)
        assertEquals("4,5", myList.joinToString(","))
    }

    /*
    @Test
    fun `DateTime-fromTsToHHmm() should work`() {
        val ts = 1722032842L
        assertEquals("00:27", DateTime.fromTsToHHmm(ts))
    }
    */

    @Test
    fun `DateTime-getDelay() should work`() {
        assertEquals("00:01:40", DateTime.getDelay(1722032842L, 1722032942L))
    }

    /*
    @Test
    fun `DateTime-fromLocalDateTimeToHHmmss() should work`() {
        val ts = 1722032842L
        val localDateTime = LocalDateTime.ofEpochSecond(ts, 0, OffsetDateTime.now().offset)
        assertEquals("00:27:22", DateTime.fromLocalDateTimeToHHmmss(localDateTime))
    }*/

    @Test
    fun `Fichier-getFilesizeForHuman() should return '0 o'`() {
        val file = Fichier(name = "", size = 0)
        assertEquals("0 o", file.getFilesizeForHuman())
    }

    /*
    @Test
    fun `Fichier-getFilesizeForHuman() should return '1,00 Ko'`() {
        val file = Fichier(name = "", size = 1024)
        assertEquals("1,00 Ko", file.getFilesizeForHuman())
    }

    @Test
    fun `Fichier-getFilesizeForHuman() should return '1,48 Ko'`() {
        val file = Fichier(name = "", size = 1519)
        assertEquals("1,48 Ko", file.getFilesizeForHuman())
    }

    @Test
    fun `Fichier-getFilesizeForHuman() should return '1483,93 Ko'`() {
        val file = Fichier(name = "", size = 1519547)
        assertEquals("1483,93 Ko", file.getFilesizeForHuman())
    }*/
}