/**
 * DO NOT EDIT
 * See android-lib project
 */
package org.inspir3.common

import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

class DateTime {
    companion object {
        /**
         * Return the number of seconds since 01/01/1970 at Europe/Paris
         */
        fun getTs(): Long {
            val zoneOffSet: ZoneOffset = OffsetDateTime.now().offset
            return LocalDateTime.now().toEpochSecond(zoneOffSet)
        }

        /**
         * Return 04:09:18
         * @param startTs Long E.g. Instant.now().epochSecond
         * @param endTs Long E.g. Instant.now().epochSecond
         */
        fun getDelay(startTs: Long, endTs: Long): String {
            val startTemporal = Instant.ofEpochSecond(startTs)
            val endTemporal = Instant.ofEpochSecond(endTs)
            val duration = Duration.between(startTemporal, endTemporal)
            return String.format(
                Locale.FRANCE,
                "%02d:%02d:%02d",
                duration.toHours(),
                duration.toMinutes() % 60,
                duration.seconds % 60,
                )
        }

        fun fromTsToHHmm(timestamp: Long): String {
            val localDateTime = LocalDateTime.ofEpochSecond(timestamp, 0, OffsetDateTime.now().offset)
            val dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
            return dateTimeFormatter.format(localDateTime)
        }

        fun fromLocalDateTimeToHHmmss(localDateTime: LocalDateTime): String {
            val dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
            return dateTimeFormatter.format(localDateTime)
        }
    }
}
