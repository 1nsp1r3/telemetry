package org.inspir3.telemetry

import org.inspir3.common.DateTime
import org.inspir3.common.file.Fichier
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

    @Test
    fun `Fichier-getFilesizeForHuman() should return '0 o'`() {
        val file = Fichier(name = "", size = 0)
        assertEquals("0 o", file.getFilesizeForHuman())
    }

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
    }
}