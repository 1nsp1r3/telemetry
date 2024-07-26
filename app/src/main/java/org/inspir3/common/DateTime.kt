/**
 * DO NOT EDIT
 * See android-lib project
 */
package org.inspir3.common

import java.time.Duration
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

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
         * Return 04h09:18
         */
        fun getDelay(startTime: LocalDateTime, endTime: LocalDateTime): String {
            val duration = Duration.between(startTime, endTime)
            val hours = duration.toHoursPart().toString().padStart(2, '0')
            val minutes = duration.toMinutesPart().toString().padStart(2, '0')
            val seconds = duration.toSecondsPart().toString().padStart(2, '0')
            return "${hours}h$minutes:$seconds"
        }

        fun fromTsToHHmm(timestamp: Long): String {
            val dateTime = LocalDateTime.ofEpochSecond(timestamp, 0, OffsetDateTime.now().offset)
            val dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
            return dateTimeFormatter.format(dateTime)
        }
    }
}
