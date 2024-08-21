package org.inspir3.telemetry

import java.time.LocalDateTime

object Debug {
    var lastBleReception: LocalDateTime = LocalDateTime.MIN
    var bleCount: Int = 0
}
