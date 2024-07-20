/**
 * DO NOT EDIT
 * See android-lib project
 */
package org.inspir3.common

import java.time.Duration
import java.time.LocalDateTime

class DateTime {
    companion object {
        fun getDelay(startTime: LocalDateTime, endTime: LocalDateTime): String {
            val duration = Duration.between(startTime, endTime)
            val hours = duration.toHoursPart().toString().padStart(2, '0')
            val minutes = duration.toMinutesPart().toString().padStart(2, '0')
            val seconds = duration.toSecondsPart().toString().padStart(2, '0')
            return "${hours}h$minutes:$seconds"
        }
    }
}
